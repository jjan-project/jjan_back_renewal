package jjan_back_renewal.user.service;

import jjan_back_renewal.user.dto.UserDto;
import jjan_back_renewal.user.entitiy.UserEntity;
import jjan_back_renewal.user.repository.UserRepository;

public interface UserService {
    UserEntity register(UserDto userDto);
    UserEntity findByEmail(String email);
    UserEntity findByNickName(String nickName);
}
