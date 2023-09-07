package com.team.jjan.party.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String address;

    private double latitude;    //위도

    private double longitude;   //경도
}
