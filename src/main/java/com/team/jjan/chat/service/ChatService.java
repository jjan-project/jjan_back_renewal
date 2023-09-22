package com.team.jjan.chat.service;

import com.team.jjan.chat.dto.ChatResponse;
import com.team.jjan.chat.dto.ChatRoomDTO;
import com.team.jjan.chat.dto.ChatRoomResponse;
import com.team.jjan.chat.repository.ChatRepository;
import com.team.jjan.jwt.support.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final JwtProvider jwtProvider;


    public List<ChatRoomResponse> findChattingRoomByUserId(String accessToken) {
        String userEmail = jwtProvider.getUserEmail(accessToken);

        List<ChatRoomResponse> result = chatRepository.findChatRoomByUserEmail(userEmail)
                .stream().map(ChatRoomResponse::new).collect(Collectors.toList());

        return result;
    }

    public List<ChatResponse> findChatDataByPartyId(long partyId) {
        return chatRepository.findChatDataByPartyId(partyId);
    }

}
