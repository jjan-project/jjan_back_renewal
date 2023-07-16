package com.team.jjan.join.dto;

import com.team.jjan.common.GenericResponse;
import com.team.jjan.user.dto.JoinResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends GenericResponse {
    private JoinResponse joinResponse;
    private TokenResponse token;
}
