package com.team.jjan.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    private String address;

    private double latitude;

    private double longitude;

}
