package com.team.jjan.party.repository;

import com.team.jjan.party.entity.PartyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<PartyEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"author"})
    Optional<PartyEntity> findById(Long aLong);

    @EntityGraph(attributePaths = {"author"})
    @Query("select party from PartyEntity party left join party.joinUser party_join left join party_join.joinUser user")
    List<PartyEntity> findAllParty(Pageable pageable);
}
