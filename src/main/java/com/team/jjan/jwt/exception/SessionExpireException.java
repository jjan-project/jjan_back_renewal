package com.team.jjan.jwt.exception;

public class SessionExpireException extends RuntimeException {
    public SessionExpireException(String message) {
        super(message);
    }
}
