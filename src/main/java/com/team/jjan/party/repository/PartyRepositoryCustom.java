package com.team.jjan.party.repository;

import com.team.jjan.party.dto.request.PartySearchCondition;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.user.entitiy.UserEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PartyRepositoryCustom {

    List<PartyEntity> findAllBySearch(Pageable pageable, PartySearchCondition searchCondition, UserEntity user);

    Optional<PartyEntity> findPartyAndChatById(long partyId);

}
