package com.team.jjan.party.dto.request;

import com.team.jjan.party.entity.PartyTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartySearchCondition {

    //정렬 기준
    private SortSelection sort;

    //파티 태그
    private List<PartyTag> partyTagList = new ArrayList<>();

    //동네 반경 범위
    private Integer radiusRange;

    //최소 인원
    private Integer personnelGoe;

    //최대 인원
    private Integer personnelLoe;

    //연령대
    private AgeTag ageTag;

}
