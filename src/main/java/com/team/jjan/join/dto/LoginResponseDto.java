package com.team.jjan.join.dto;

import com.team.jjan.common.GenericResponse;
import com.team.jjan.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto extends GenericResponse {
    private UserDto userDto;
    private TokenDto token;
}
