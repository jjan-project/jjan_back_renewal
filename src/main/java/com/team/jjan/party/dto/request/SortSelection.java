package com.team.jjan.party.dto.request;


import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SortSelection {

    Sort_Time("최신순"), Sort_Location("가까운 위치 순");

    @JsonValue
    private String sorting;

}
