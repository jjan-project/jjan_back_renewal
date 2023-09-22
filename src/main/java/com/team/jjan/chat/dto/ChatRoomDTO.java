package com.team.jjan.chat.dto;

import com.team.jjan.party.entity.PartyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatRoomDTO {
    private long chatId;
    private PartyEntity party;
}
