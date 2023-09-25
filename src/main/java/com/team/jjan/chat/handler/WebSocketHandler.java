package com.team.jjan.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.jjan.chat.dto.ChatRequest;
import com.team.jjan.chat.dto.ChatResponse;
import com.team.jjan.chat.dto.MessageType;
import com.team.jjan.chat.entity.Chat;
import com.team.jjan.chat.repository.ChatRepository;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.exception.NoSuchPartyException;
import com.team.jjan.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team.jjan.chat.dto.ChatResponse.createChatResponse;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private Map<Long , List<WebSocketSession>> sessionList = new HashMap<>();
    private final PartyRepository partyRepository;
    private final ChatRepository chatRepository;

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatRequest chatMessage = objectMapper.readValue(message.getPayload(), ChatRequest.class);
        long partyId = chatMessage.getPartyId();

        if(chatMessage.getMessageType() == MessageType.ENTER) {
            joinChatBySession(partyId , session);

        } else if(chatMessage.getMessageType() == MessageType.SEND) {
            sendChatToSameRootId(partyId , objectMapper , chatMessage);
        }
    }

    private void joinChatBySession(long partyId , WebSocketSession session) {
        if(!sessionList.containsKey(partyId)) {
            sessionList.put(partyId , new ArrayList<>());
        }
        List<WebSocketSession> sessions = sessionList.get(partyId);
        if(!sessions.contains(session)) {
            sessions.add(session);
        }
    }

    private void sendChatToSameRootId(long partyId , ObjectMapper objectMapper , ChatRequest chatMessage) throws IOException {
        List<WebSocketSession> sessions = sessionList.get(partyId);

        for(WebSocketSession webSocketSession : sessions) {
            ChatResponse chatResponse = createChatResponse(chatMessage);
            saveChatData(partyId , chatResponse);

            String result = objectMapper.writeValueAsString(chatResponse);
            webSocketSession.sendMessage(new TextMessage(result));
        }
    }

    private void saveChatData(long partyId , ChatResponse chatResponse) {
        PartyEntity party = partyRepository.findPartyAndChatById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("파티 정보를 찾을 수 없습니다."));

        party.updateListChat(chatResponse.getMessage());
        Chat chat = Chat.createChat(chatResponse , party);
        party.getChatList().add(chat);
        chatRepository.save(chat);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        for(Long partyId : sessionList.keySet()) {
            List<WebSocketSession> webSocketSessions = sessionList.get(partyId);

            for(int i = 0; i < webSocketSessions.size(); i++) {
                WebSocketSession socket = webSocketSessions.get(i);

                if(socket.getId().equals(session.getId())) {
                    webSocketSessions.remove(i);
                    break;
                }
            }
        }
    }
}