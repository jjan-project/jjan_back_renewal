package com.team.jjan.party.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.jjan.party.entity.Location;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.party.entity.PartyTag;
import com.team.jjan.partyJoin.entity.PartyJoin;
import com.team.jjan.user.entitiy.UserEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyGetResponseDto {
    private Long id;
    private List<UserInfo> joinUser = new ArrayList<>();
    private int male=0;
    private int female=0;
    private String title;
    private String content;
    private Location location;
    private int maxPartyNum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    private Date partyDate;
    private List<PartyTag> partyTags = new ArrayList<>();
    private List<String> partyImages = new ArrayList<>();

    public PartyGetResponseDto(PartyEntity party, List<PartyJoin> partyJoins){
        this.id = party.getId();
        this.title = party.getTitle();
        this.content = party.getContent();
        this.location = party.getLocation();
        this.maxPartyNum = party.getMaxPartyNum();
        this.partyDate = party.getPartyDate();
        this.partyTags = party.getPartyTags();
        this.partyImages = party.getPartyImages();

        maleOrFemale(party.getAuthor());
        this.joinUser.add(UserInfo.of(party.getAuthor()));

        for (PartyJoin join : partyJoins) {
            maleOrFemale(join.getJoinUser());
            this.joinUser.add(UserInfo.of(join.getJoinUser()));
        }
    }

    private void maleOrFemale(UserEntity user){
        if(user.getGender().equals("남성")) this.male++;
        else this.female++;
    }
}

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
class UserInfo{
    private Long id;
    private String profile;

    public static UserInfo of(UserEntity user){
        return UserInfo.builder()
                .id(user.getId())
                .profile(user.getProfile())
                .build();
    }
}
