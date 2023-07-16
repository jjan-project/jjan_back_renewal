package jjan_back_renewal.jwt.exception;

public class SessionExpireException extends RuntimeException {
    public SessionExpireException(String message) {
        super(message);
    }
}
