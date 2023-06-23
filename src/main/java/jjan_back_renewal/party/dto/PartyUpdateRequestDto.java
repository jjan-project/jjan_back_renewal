package jjan_back_renewal.party.dto;

import jjan_back_renewal.user.entitiy.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartyUpdateRequestDto {
    private Long id;
    private UserEntity userEntity;
    private String title;
    private String content;
    private int maxPartyNum;
    private String partyLocation;
    private String partyDate;
}
