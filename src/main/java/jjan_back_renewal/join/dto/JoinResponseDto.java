package jjan_back_renewal.join.dto;

import jjan_back_renewal.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinResponseDto {
    private UserDto userDto;
    private String token;
}
