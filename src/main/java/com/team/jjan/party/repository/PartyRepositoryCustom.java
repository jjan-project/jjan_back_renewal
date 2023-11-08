package com.team.jjan.party.repository;

import com.team.jjan.party.dto.request.PartySearchCondition;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.user.entitiy.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PartyRepositoryCustom {

    Page<PartyEntity> findAllBySearch(Pageable pageable, PartySearchCondition searchCondition, UserEntity user);

    Optional<PartyEntity> findPartyAndChatById(long partyId);

}
