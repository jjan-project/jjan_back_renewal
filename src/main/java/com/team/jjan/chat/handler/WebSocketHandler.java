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
        long meetingId = chatMessage.getMeetingId();

        if(chatMessage.getMessageType() == MessageType.ENTER) {
            joinChatBySession(meetingId , session);

        } else if(chatMessage.getMessageType() == MessageType.SEND) {
            sendChatToSameRootId(meetingId , objectMapper , chatMessage);
        }
    }

    private void joinChatBySession(long meetingId , WebSocketSession session) {
        if(!sessionList.containsKey(meetingId)) {
            sessionList.put(meetingId , new ArrayList<>());
        }
        List<WebSocketSession> sessions = sessionList.get(meetingId);
        if(!sessions.contains(session)) {
            sessions.add(session);
        }
    }

    private void sendChatToSameRootId(long meetingId , ObjectMapper objectMapper , ChatRequest chatMessage) throws IOException {
        List<WebSocketSession> sessions = sessionList.get(meetingId);

        for(WebSocketSession webSocketSession : sessions) {
            ChatResponse chatResponse = createChatResponse(chatMessage);
            saveChatData(meetingId , chatResponse);

            String result = objectMapper.writeValueAsString(chatResponse);
            webSocketSession.sendMessage(new TextMessage(result));
        }
    }

    private void saveChatData(long partyId , ChatResponse chatResponse) {
        PartyEntity party = partyRepository.findById(partyId)
                .orElseThrow(() -> new NoSuchPartyException("파티 정보를 찾을 수 없습니다."));

        Chat chat = Chat.createChat(chatResponse , party);
        party.getChatList().add(chat);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        for(Long meetingId : sessionList.keySet()) {
            List<WebSocketSession> webSocketSessions = sessionList.get(meetingId);

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