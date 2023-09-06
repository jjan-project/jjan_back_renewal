package com.team.jjan.party.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.jjan.party.entity.Location;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.entity.PartyTag;
import com.team.jjan.user.entitiy.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PartyCreateRequestDto {
    private String title;
    private String content;
    private int maxPartyNum;
    private double partyLongitude;  //경도
    private double partyLatitude;   //위도
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    private Date partyDate;
    private List<PartyTag> partyTags;

    public PartyEntity toEntity(UserEntity author, List<String> partyImages){
        return PartyEntity.builder()
                .title(title)
                .content(content)
                .author(author)
                .maxPartyNum(maxPartyNum)
                .location(new Location(partyLongitude, partyLatitude))
                .partyDate(partyDate)
                .partyTags(partyTags)
                .partyImages(partyImages)
                .averageAge((long)LocalDate.now().getYear()-author.getBirth().getYear()+1-1900)
                .build();
    }
}
