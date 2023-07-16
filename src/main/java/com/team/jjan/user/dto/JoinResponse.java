package com.team.jjan.user.dto;

import com.team.jjan.user.entitiy.Role;
import com.team.jjan.user.entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class JoinResponse {
    private Long id;
    private String email;
    private String nickName;
    private String password;
    private String profile;
    private String address;
    private String gender;
    private Date birth;
    private String drinkCapacity;
    private boolean isNickNameChangeAvailable = true;
    private Role roles;

    public JoinResponse(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.nickName = userEntity.getNickName();
        this.password = userEntity.getPassword();
        this.profile = userEntity.getProfile();
        this.address = userEntity.getAddress();
        this.gender = userEntity.getGender();
        this.birth = userEntity.getBirth();
        this.drinkCapacity = userEntity.getDrinkCapacity();
        this.roles = userEntity.getRoles();
        this.isNickNameChangeAvailable = userEntity.isNickNameChangeAvailable();
    }
}

