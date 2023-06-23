package jjan_back_renewal.party.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PartyCreateRequestDto {
    private String title;
    private String content;
    private int maxPartyNum;
    private String partyLocation;
    private String partyDate;
}
