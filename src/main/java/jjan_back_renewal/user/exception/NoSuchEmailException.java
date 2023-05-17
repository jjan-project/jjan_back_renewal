package jjan_back_renewal.user.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchEmailException extends RuntimeException {
    public NoSuchEmailException(String message) {
        super(message);
    }
}
