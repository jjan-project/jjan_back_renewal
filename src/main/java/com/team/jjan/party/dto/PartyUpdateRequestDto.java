package com.team.jjan.party.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private double partyLatitude;   //위도
    private double partyLongitude;  //경도
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/mm/dd", timezone = "Asia/Seoul")
    private Date partyDate;
    private List<PartyTag> partyTags = new ArrayList<>();
}
