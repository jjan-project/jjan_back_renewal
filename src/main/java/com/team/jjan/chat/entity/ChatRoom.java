package com.team.jjan.chat.entity;

import com.team.jjan.party.entity.PartyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChatRoom {

    @Id
    private long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    private PartyEntity party;

    @CreationTimestamp
    private LocalDate createDate;

    public static ChatRoom createChatRoom(PartyEntity party) {
        return ChatRoom.builder()
                .chatId(party.getId())
                .party(party)
                .build();
    }

}