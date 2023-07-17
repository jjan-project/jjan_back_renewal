package com.team.jjan.join.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class JoinRequest {
    private String email;
    private String password;
    private String address;
    private Date birth;
    private String gender;
    private String nickname;
    private String drinkingCapacity;
}
