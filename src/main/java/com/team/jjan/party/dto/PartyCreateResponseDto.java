package com.team.jjan.party.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class PartyCreateResponseDto {

    private Long id;
    private String author;
    private String authorEmail;
    private String title;
    private String content;
    private int maxPartyNum;
    private Location location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    private Date partyDate;
    private List<PartyTag> partyTags = new ArrayList<>();
    private List<String> partyImages = new ArrayList<>();

    public static PartyCreateResponseDto of(PartyEntity partyEntity){
        return PartyCreateResponseDto.builder()
                .id(partyEntity.getId())
                .author(partyEntity.getAuthor().getNickName())
                .authorEmail(partyEntity.getAuthor().getEmail())
                .title(partyEntity.getTitle())
                .content(partyEntity.getContent())
                .maxPartyNum(partyEntity.getMaxPartyNum())
                .location(partyEntity.getLocation())
                .partyDate(partyEntity.getPartyDate())
                .partyTags(partyEntity.getPartyTags())
                .partyImages(partyEntity.getPartyImages())
                .build();
    }
}
