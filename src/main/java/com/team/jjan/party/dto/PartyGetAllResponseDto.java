package com.team.jjan.party.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.jjan.party.entity.PartyEntity;
import com.team.jjan.partyJoin.entity.PartyJoin;
import com.team.jjan.user.entitiy.UserEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PartyGetAllResponseDto {
    private Long id;
    private List<UserInfo> joinUser = new ArrayList<>();
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    private Date partyDate;
    private String thumbnail;

    public PartyGetAllResponseDto(PartyEntity party){
        this.id = party.getId();
        this.title = party.getTitle();
        this.partyDate = party.getPartyDate();
        this.thumbnail = party.getPartyImages().get(0);

        this.joinUser.add(UserInfo.of(party.getAuthor()));

        for (PartyJoin join : party.getJoinUser()) {
            this.joinUser.add(UserInfo.of(join.getJoinUser()));
        }
    }
}
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class UserInfo {
    private Long id;
    private String profile;

    public static UserInfo of(UserEntity user) {
        return UserInfo.builder()
                .id(user.getId())
                .profile(user.getProfile())
                .build();
    }
}
