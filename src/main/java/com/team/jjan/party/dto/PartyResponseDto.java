package com.team.jjan.party.dto;

import com.team.jjan.common.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PartyResponseDto extends GenericResponse {
    private PartyDto party;
}
