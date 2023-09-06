package com.team.jjan.party.dto.request;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum AgeTag {
    TAG_20_1("20대 초반", 20, 23), TAG_20_2("20대 중반", 24, 26), TAG_20_3("20대 후반", 27, 29),
    TAG_30_1("30대 초반", 30, 33), TAG_30_2("30대 중반", 34, 36), TAG_NULL(null, 0,0);

    @JsonValue
    private String tagName;
    private Integer startAge;
    private Integer endAge;
}
