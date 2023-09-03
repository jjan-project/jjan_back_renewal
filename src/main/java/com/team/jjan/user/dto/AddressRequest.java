package com.team.jjan.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressRequest {

    private String address;

    private double locateX;

    private double locateY;

}
