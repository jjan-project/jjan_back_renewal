package jjan_back_renewal.party.dto;

import jjan_back_renewal.config.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PartyCreateResponseDto extends GenericResponse {
    private PartyDto party;
}
