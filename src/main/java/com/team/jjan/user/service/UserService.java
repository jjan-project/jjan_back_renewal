package com.team.jjan.user.service;

import com.team.jjan.user.dto.JoinResponse;

public interface UserService {

    JoinResponse findByEmail(String email);

    JoinResponse findByNickName(String nickName);

    JoinResponse setNickName(String userEmail, String nickName);

    JoinResponse setDrinkCapacity(String userEmail, String capacity);

    Long isDuplicatedNickName(String nickName);

    Long isDuplicatedEmail(String email);

    boolean isReplaceableUser(String email);
}
