package jjan_back_renewal.config;

import jjan_back_renewal.party.exception.NoSuchPartyException;
import jjan_back_renewal.upload.FileUploadException;
import jjan_back_renewal.user.exception.NoSuchEmailException;
import jjan_back_renewal.user.exception.NoSuchNicknameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchEmailException.class)
    public ResponseEntity<GenericResponse> noSuchEmailError(NoSuchEmailException e) {
        log.info("존재하지 않는 이메일 검사 : {}", e.getMessage());
        GenericResponse response = new GenericResponse(403, "FORBIDDEN");
        return ResponseEntity.ok().body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchNicknameException.class)
    public ResponseEntity<GenericResponse> noSuchNicknameError(NoSuchNicknameException e) {
        log.info("존재하지 않는 닉네임 검사 : {}", e.getMessage());
        GenericResponse response = new GenericResponse(403, "FORBIDDEN");
        return ResponseEntity.ok().body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FileUploadException.class)
    public ResponseEntity<GenericResponse> fileConversionError(FileUploadException e) {
        log.error(e.getMessage());
        GenericResponse response = new GenericResponse(400, "BAD REQUEST");
        return ResponseEntity.ok().body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchPartyException.class)
    public ResponseEntity<GenericResponse> noSuchPartyException(NoSuchPartyException e) {
        log.error(e.getMessage());
        GenericResponse response = new GenericResponse(400, "BAD REQUEST");
        return ResponseEntity.ok().body(response);
    }
}
