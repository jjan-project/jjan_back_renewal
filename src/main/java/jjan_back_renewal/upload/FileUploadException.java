package jjan_back_renewal.upload;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }
}
