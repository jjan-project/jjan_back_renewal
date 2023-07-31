package com.team.jjan.party.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum PartyTag {
    TAG_00("외향적인"), TAG_01("내향적인"), TAG_02("술꾼들"),
    TAG_03("알쓰"), TAG_04("시끄러운"), TAG_05("조용한"),
    TAG_06("친구끼리"), TAG_07("소통"), TAG_08("칵테일바"),
    TAG_09("파티룸"), TAG_10("와인바"), TAG_11("술집"),
    TAG_12("미팅"), TAG_13("맥주"), TAG_14("대학생"),
    TAG_15("직장인"), TAG_16("친해져요");

    @JsonValue
    private String tagName;

    public static PartyTag of(String tagName) {
        for (PartyTag tag : PartyTag.values()) {
            if (tag.tagName.equals(tagName)) {
                return tag;
            }
        }
        throw new IllegalArgumentException("일치하는 태그가 없습니다. : " + tagName);
    }
}