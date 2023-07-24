package com.team.jjan.party.dto;

import com.team.jjan.party.entity.Location;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.entity.PartyTag;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PartyUpdateResponseDto {

    private String title;
    private String content;
    private Location location;
    private int maxPartyNum;
    private Date partyDate;
    private List<PartyTag> partyTags = new ArrayList<>();
    private List<String> partyImages = new ArrayList<>();

    public static PartyUpdateResponseDto of(PartyEntity partyEntity, List<String> partyImages){
        return PartyUpdateResponseDto.builder()
                .title(partyEntity.getTitle())
                .content(partyEntity.getContent())
                .location(partyEntity.getLocation())
                .maxPartyNum(partyEntity.getMaxPartyNum())
                .partyDate(partyEntity.getPartyDate())
                .partyTags(partyEntity.getPartyTags())
                .partyImages(partyImages)
                .build();
    }
}
