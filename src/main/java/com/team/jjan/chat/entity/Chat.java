package com.team.jjan.chat.entity;

import com.team.jjan.chat.dto.ChatResponse;
import com.team.jjan.party.entity.PartyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    private PartyEntity party;

    private String message;

    private String sendTime;

    private String sender;

    private String senderImage;

    public static Chat createChat(ChatResponse chatResponse , PartyEntity party) {
        return Chat.builder()
                .message(chatResponse.getMessage())
                .sendTime(chatResponse.getSendTime())
                .party(party)
                .sender(chatResponse.getSender())
                .senderImage(chatResponse.getSenderImage())
                .build();
    }
}
