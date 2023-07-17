package com.team.jjan.upload.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }
}
