package com.team.jjan.join.dto;

import com.team.jjan.common.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResponseDto extends GenericResponse {
    String email;
}
