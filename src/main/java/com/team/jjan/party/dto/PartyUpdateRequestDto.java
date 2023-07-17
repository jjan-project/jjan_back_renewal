package com.team.jjan.party.dto;

import com.team.jjan.party.entity.PartyTag;
import com.team.jjan.user.entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartyUpdateRequestDto {
    private Long id;
    private UserEntity userEntity;
    private String title;
    private String content;
    private int maxPartyNum;
    private double partyLatitude;
    private double partyLongitude;
    private String partyDate;
    private List<PartyTag> partyTags = new ArrayList<>();
}
