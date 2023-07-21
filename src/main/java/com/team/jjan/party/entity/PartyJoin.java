package com.team.jjan.party.entity;

import com.team.jjan.user.entitiy.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

}
