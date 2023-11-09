package com.team.jjan.party.repository;

import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.user.entitiy.UserEntity;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<PartyEntity, Long>, PartyRepositoryCustom {

    @Override
    @EntityGraph(attributePaths = {"author"})
    Optional<PartyEntity> findById(Long aLong);

    @EntityGraph(attributePaths = {"author", "joinUser", "joinUser.joinUser"})
    List<PartyEntity> findByAuthor(UserEntity author);

    @EntityGraph(attributePaths = {"author", "joinUser", "joinUser.joinUser"})
    @Query("select party from PartyEntity party")
    Page<PartyEntity> findAllParty(Pageable pageable);

    @EntityGraph(attributePaths = {"author"})
    @Query("select party from PartyEntity party left join fetch party.joinUser party_join left join fetch party_join.joinUser user where user=:user")
    List<PartyEntity> findMyJoinParty(@Param("user") UserEntity user);
}
