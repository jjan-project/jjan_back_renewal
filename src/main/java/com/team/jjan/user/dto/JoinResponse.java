package com.team.jjan.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.jjan.user.entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class JoinResponse {
    private String email;
    private String nickName;
    private String profile;
    private String address;
    private String gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy/MM/dd" , timezone = "Asia/Seoul")
    private Date birth;
    private String drinkCapacity;

    public JoinResponse(UserEntity userEntity) {
        this.email = userEntity.getEmail();
        this.nickName = userEntity.getNickName();
        this.profile = userEntity.getProfile();
        this.address = userEntity.getAddress();
        this.gender = userEntity.getGender();
        this.birth = userEntity.getBirth();
        this.drinkCapacity = userEntity.getDrinkCapacity();
    }
}

