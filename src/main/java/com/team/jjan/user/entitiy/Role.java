package com.team.jjan.user.entitiy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_MEMBER("ROLE_MEMBER", "일반 사용자"), ROLE_ADMIN("ROLE_ADMIN", "권한 사용자");

    private final String role;
    private final String title;
}
