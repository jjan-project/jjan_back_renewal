package com.team.jjan.join.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
    private String email;
    private String password;
    private String address;
    private Date birth;
    private double latitude;
    private double longitude;
    private String gender;
    private String nickname;
    private String drinkingCapacity;
}
