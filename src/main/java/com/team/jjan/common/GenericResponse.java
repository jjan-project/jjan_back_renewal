package com.team.jjan.common;

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

}
