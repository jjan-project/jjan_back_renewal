package com.team.jjan.user.repository;

import com.team.jjan.user.entitiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    void deleteByEmail(String email);

    Optional<UserEntity> findByNickName(String nickName);
}
