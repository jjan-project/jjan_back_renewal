package com.team.jjan.jwt.exception;

public class TokenForgeryException extends RuntimeException {
    public TokenForgeryException(String message) {
        super(message);
    }

}
