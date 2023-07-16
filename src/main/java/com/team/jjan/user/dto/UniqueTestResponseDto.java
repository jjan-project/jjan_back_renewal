package com.team.jjan.user.dto;

import com.team.jjan.common.GenericResponse;
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
