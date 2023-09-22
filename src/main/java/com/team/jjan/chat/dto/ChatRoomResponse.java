package com.team.jjan.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatRoomResponse {
    private long chatId;

    private long partyId;

    private String partyTitle;

}