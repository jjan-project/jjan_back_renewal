package com.team.jjan.join.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String key;
}
