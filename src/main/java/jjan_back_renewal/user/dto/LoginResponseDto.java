package jjan_back_renewal.user.dto;

import jjan_back_renewal.config.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto extends GenericResponse {
    private UserDto userDto;
    private String token;
}
