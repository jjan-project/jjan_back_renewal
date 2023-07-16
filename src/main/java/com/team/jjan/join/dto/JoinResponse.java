package com.team.jjan.join.dto;

import com.team.jjan.common.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinResponse extends GenericResponse {
    private JoinResponse joinResponse;
    private TokenResponse token;
}
