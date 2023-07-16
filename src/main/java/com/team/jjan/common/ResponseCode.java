package com.team.jjan.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    REQUEST_FAIL(-1 , "유효하지 않는 요청입니다."),
    RESPONSE_FAIL(-1 , "요청에 응답하지 못했습니다."),
    AUTHENTICATION_FAIL(-2 , "접근 권한이 없습니다."),
    AUTHORIZATION_FAIL(-2 , "인증되지 않은 사용자입니다."),
    REQUEST_SUCCESS(1 , "요청이 성공적으로 완료되었습니다.");

    private final int code;
    private final String message;
}
