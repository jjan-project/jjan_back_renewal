package com.team.jjan.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatRoomResponse {
    private long chatId;

    private long partyId;

    private String partyTitle;

    private String partyImages;

    private String lastChat;

    public ChatRoomResponse(ChatRoomDTO chatRoomDTO) {
        this.chatId = chatRoomDTO.getChatId();
        this.partyId = chatRoomDTO.getParty().getId();
        this.partyTitle = chatRoomDTO.getParty().getTitle();
        this.partyImages = !chatRoomDTO.getParty().getPartyImages().isEmpty()? chatRoomDTO.getParty().getPartyImages().get(0):null;
        this.lastChat = chatRoomDTO.getParty().getLastChat();
    }
}