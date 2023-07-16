package jjan_back_renewal.join.dto;

import jjan_back_renewal.common.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResponseDto extends GenericResponse {
    String email;
}
