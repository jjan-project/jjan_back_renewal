package jjan_back_renewal.party.dto;

import jjan_back_renewal.party.entity.PartyEntity;
import jjan_back_renewal.user.entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String partyLocation;
    private String partyDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PartyDto(UserEntity userEntity, PartyCreateRequestDto createRequestDto) {
        this.setAuthor(userEntity);
        this.setTitle(createRequestDto.getTitle());
        this.setContent(createRequestDto.getContent());
        this.setPartyDate(createRequestDto.getPartyDate());
        this.setPartyLocation(createRequestDto.getPartyLocation());
    }

    public PartyDto(PartyEntity entity) {
        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.maxPartyNum = entity.getMaxPartyNum();
        this.partyLocation = entity.getPartyLocation();
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
                .partyLocation(partyLocation)
                .partyDate(partyDate)
                .build();
    }
}