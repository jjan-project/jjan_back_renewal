package com.team.jjan.party.repository;

import com.team.jjan.party.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<PartyEntity, Long> {
}
