package com.team.jjan.chat.repository;

import com.team.jjan.chat.dto.ChatResponse;
import com.team.jjan.chat.dto.ChatRoomResponse;

import java.util.List;

public interface CustomChatRepository {

    List<ChatResponse> findChatDataByPartyId(long findChatDataByPartyId);

    List<ChatRoomResponse> findChatRoomByUserEmail(String userEmail);

}
