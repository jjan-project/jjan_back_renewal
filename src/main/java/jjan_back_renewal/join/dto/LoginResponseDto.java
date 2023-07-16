package jjan_back_renewal.join.dto;

import jjan_back_renewal.common.GenericResponse;
import jjan_back_renewal.user.dto.UserDto;
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
