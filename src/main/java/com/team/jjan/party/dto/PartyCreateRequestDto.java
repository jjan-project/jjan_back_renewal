package com.team.jjan.party.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PartyCreateRequestDto {
    private String title;
    private String content;
    private int maxPartyNum;
    private double partyLatitude;
    private double partyLongitude;
    private String partyDate;
    private List<String> partyTags;
}