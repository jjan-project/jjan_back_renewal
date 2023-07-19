package com.team.jjan.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseMessage<T> {
    private int code;
    private String message;
    private T data;
    public static ResponseMessage of(ResponseCode responseCode) {
        return ResponseMessage.builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .build();
    }

    public static ResponseMessage of(ResponseCode responseCode , String message) {
        return ResponseMessage.builder()
                .code(responseCode.getCode())
                .message(message)
                .build();
    }

    public static <T>ResponseMessage of(ResponseCode responseCode , T data) {
        return ResponseMessage.builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .data(data)
                .build();
    }

    public static <T>ResponseMessage of(ResponseCode responseCode , String message , T data) {
        return ResponseMessage.builder()
                .code(responseCode.getCode())
                .message(message)
                .data(data)
                .build();
    }
}
