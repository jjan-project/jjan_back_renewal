package jjan_back_renewal.user.service;

import jjan_back_renewal.user.dto.LoginRequestDto;
import jjan_back_renewal.user.dto.UserDto;

public interface UserService {
    UserDto login(LoginRequestDto loginRequestDto);

    UserDto register(UserDto userDto);

    UserDto findByEmail(String email);

    UserDto findByNickName(String nickName);

    Long isDuplicatedNickName(String nickName);
}
