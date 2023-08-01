package com.team.jjan.party.repository;

import com.team.jjan.party.entity.PartyEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyRepository extends JpaRepository<PartyEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"author"})
    Optional<PartyEntity> findById(Long aLong);
}
