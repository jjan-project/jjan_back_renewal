package com.team.jjan.party.entity;

import com.team.jjan.user.entitiy.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "party_join")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PartyJoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity joinUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private PartyEntity joinParty;

    public static PartyJoin createJoin(UserEntity user, PartyEntity party){
        PartyJoin partyJoin = PartyJoin.builder()
                .joinUser(user)
                .joinParty(party)
                .build();



        return partyJoin;
    }
}
