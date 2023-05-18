package jjan_back_renewal.user.dto;

import jjan_back_renewal.config.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UniqueTestResponseDto extends GenericResponse {
    private String target;
    private String item;
}
