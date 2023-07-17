package com.team.jjan.jwt.repository;

import com.team.jjan.jwt.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Query(value = "SELECT p from RefreshToken p where p.keyEmail = :userEmail")
    Optional<RefreshToken> existsByKeyEmail(@Param("userEmail") String userEmail);

    void deleteByKeyEmail(String userEmail);
}
