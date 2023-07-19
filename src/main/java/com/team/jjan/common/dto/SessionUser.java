package com.team.jjan.common.dto;

import com.team.jjan.user.entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SessionUser {

    private Long id;

    private String email;

    private String nickName;

    public static SessionUser of(UserEntity user){
        return new SessionUser(user.getId(), user.getEmail(), user.getNickName());
    }
}
