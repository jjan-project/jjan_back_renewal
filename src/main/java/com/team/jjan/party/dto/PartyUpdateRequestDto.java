package com.team.jjan.party.dto;

import com.team.jjan.party.entity.Location;
import com.team.jjan.party.entity.PartyTag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PartyUpdateRequestDto {
    private String title;
    private String content;
    private int maxPartyNum;
    private Location location;
    private Date partyDate;
    private List<PartyTag> partyTags = new ArrayList<>();
}
