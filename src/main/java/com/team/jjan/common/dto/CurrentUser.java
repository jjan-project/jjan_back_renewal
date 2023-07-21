package com.team.jjan.common.dto;

import com.team.jjan.user.entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CurrentUser {

    private Long id;

    private String email;

    private String nickName;

    public static CurrentUser of(UserEntity user){
        return new CurrentUser(user.getId(), user.getEmail(), user.getNickName());
    }
}
