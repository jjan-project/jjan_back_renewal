package com.team.jjan.party.dto;

import com.team.jjan.party.entity.Location;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.entity.PartyTag;
import com.team.jjan.user.entitiy.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PartyCreateRequestDto {
    private String title;
    private String content;
    private int maxPartyNum;
    private Location location;
    private String partyDate;
    private List<PartyTag> partyTags;

    public PartyEntity toEntity(UserEntity author, List<String> partyImages){
        return PartyEntity.builder()
                .title(title)
                .content(content)
                .author(author)
                .maxPartyNum(maxPartyNum)
                .location(location)
                .partyDate(partyDate)
                .partyTags(partyTags)
                .partyImages(partyImages)
                .build();
    }
}
