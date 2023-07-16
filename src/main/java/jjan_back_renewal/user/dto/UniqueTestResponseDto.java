package jjan_back_renewal.user.dto;

import jjan_back_renewal.common.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UniqueTestResponseDto extends GenericResponse {
    private String target;
    private String item;
}
