package jjan_back_renewal.join.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiServerException extends RuntimeException {
    public ApiServerException(String message) {
        super(message);
    }
}
