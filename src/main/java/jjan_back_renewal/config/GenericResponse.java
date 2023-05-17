package jjan_back_renewal.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericResponse {
    public GenericResponse() {
        this.statusCode = 200;
        this.message = "OK";
    }

    public int statusCode;
    public String message;

    public void response403() {
        statusCode = 403;
        message = "FORBIDDEN";
    }

    public void response404() {
        statusCode = 404;
        message = "NOT FOUND";
    }
}
