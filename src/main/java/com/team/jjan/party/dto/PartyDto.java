package com.team.jjan.party.dto;

import com.team.jjan.party.entity.Location;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.entity.PartyTag;
import com.team.jjan.user.entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyDto {

    private Long id;
    private UserEntity author;
    private String title;
    private String content;
    private int maxPartyNum;
    private Location location;
    private String partyDate;
    private List<PartyTag> partyTags = new ArrayList<>();
    private List<String> partyImages = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PartyDto of(PartyEntity party){
        return PartyDto.builder()
                .id(party.getId())
                .author(party.getAuthor())
                .title(party.getTitle())
                .content(party.getContent())
                .maxPartyNum(party.getMaxPartyNum())
                .location(party.getLocation())
                .partyDate(party.getPartyDate())
                .partyTags(party.getPartyTags())
                .partyImages(party.getPartyImages())
                .createdAt(party.getCreatedAt())
                .updatedAt(party.getUpdatedAt())
                .build();
    }
}