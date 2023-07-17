package com.team.jjan.party.dto;

import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.entity.PartyTag;
import com.team.jjan.user.entitiy.UserEntity;
import lombok.*;

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
    private double partyLatitude;
    private double partyLongitude;
    private String partyDate;
    private List<PartyTag> partyTags = new ArrayList<>();
    private List<String> partyImages = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PartyDto(UserEntity userEntity, PartyCreateRequestDto createRequestDto, List<String> partyImages) {
        this.setAuthor(userEntity);
        this.setTitle(createRequestDto.getTitle());
        this.setContent(createRequestDto.getContent());
        this.setPartyLatitude(createRequestDto.getPartyLatitude());
        this.setPartyLongitude(createRequestDto.getPartyLongitude());
        for (String tag : createRequestDto.getPartyTags()) {
            partyTags.add(PartyTag.of(tag));
        }
        this.partyImages = partyImages;
    }

    public PartyDto(PartyEntity entity) {
        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.maxPartyNum = entity.getMaxPartyNum();
        this.partyLatitude = entity.getPartyLatitude();
        this.partyLongitude = entity.getPartyLongitude();
        this.partyTags = entity.getPartyTags();
        this.partyImages = entity.getPartyImages();
        this.partyDate = entity.getPartyDate();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

    public PartyEntity toEntity() {
        return PartyEntity.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .maxPartyNum(maxPartyNum)
                .partyLatitude(partyLatitude)
                .partyLongitude(partyLongitude)
                .partyDate(partyDate)
                .partyImages(partyImages)
                .partyTags(partyTags)
                .build();
    }
}