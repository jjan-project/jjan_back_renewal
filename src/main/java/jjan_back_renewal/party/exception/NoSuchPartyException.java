package jjan_back_renewal.party.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoSuchPartyException extends RuntimeException {
    public NoSuchPartyException(String message) {
        super(message);
    }
}
