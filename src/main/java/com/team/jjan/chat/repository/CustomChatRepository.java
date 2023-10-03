package com.team.jjan.chat.repository;

import com.team.jjan.chat.dto.ChatResponse;
import com.team.jjan.chat.dto.ChatRoomDTO;

import java.util.List;

public interface CustomChatRepository {

    List<ChatResponse> findChatDataByPartyId(long findChatDataByPartyId);

    List<ChatRoomDTO> findChatRoomByUserEmail(String userEmail);

}
