package com.team.jjan.party.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Location {
    private double partyLatitude;

    private double partyLongitude;
}
