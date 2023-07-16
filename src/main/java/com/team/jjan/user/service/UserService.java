package com.team.jjan.user.service;

import com.team.jjan.user.dto.UserDto;

public interface UserService {

    UserDto findByEmail(String email);

    UserDto findByNickName(String nickName);

    UserDto setNickName(String userEmail, String nickName);

    UserDto setDrinkCapacity(String userEmail, String capacity);

    Long isDuplicatedNickName(String nickName);

    Long isDuplicatedEmail(String email);

    boolean isReplaceableUser(String email);
}
