package jjan_back_renewal.user.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchNicknameException extends RuntimeException {
    public NoSuchNicknameException(String message) {
        super(message);
    }
}
