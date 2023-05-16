package jjan_back_renewal.user.repository;

import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.entitiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByEmail(String email);

    UserEntity findByNickName(String nickName);
}
