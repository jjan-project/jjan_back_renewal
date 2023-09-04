package com.team.jjan.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressRequest {

    private String address;

    private double latitude;     //위도

    private double longitude;     //경도

}
