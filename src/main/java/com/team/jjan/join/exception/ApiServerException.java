package com.team.jjan.join.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiServerException extends RuntimeException {
    public ApiServerException(String message) {
        super(message);
    }
}
