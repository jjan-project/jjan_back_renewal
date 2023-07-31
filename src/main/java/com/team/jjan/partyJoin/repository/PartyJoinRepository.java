package com.team.jjan.partyJoin.repository;

import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.partyJoin.entity.PartyJoin;
import com.team.jjan.user.entitiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyJoinRepository extends JpaRepository<PartyJoin, Long> {
    boolean existsPartyJoinByJoinUserAndJoinParty(UserEntity joinUser, PartyEntity joinParty);

    void deletePartyJoinByJoinUserAndJoinParty(UserEntity exitUser, PartyEntity exitParty);
}
