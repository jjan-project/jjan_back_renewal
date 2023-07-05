package jjan_back_renewal.party.dto;

import jjan_back_renewal.party.entity.PartyTag;
import jjan_back_renewal.user.entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartyUpdateRequestDto {
    private Long id;
    private UserEntity userEntity;
    private String title;
    private String content;
    private int maxPartyNum;
    private double partyLatitude;
    private double partyLongitude;
    private String partyDate;
    private List<PartyTag> partyTags = new ArrayList<>();
}
