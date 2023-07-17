package com.team.jjan.common;

import com.team.jjan.party.exception.NoSuchPartyException;
import com.team.jjan.upload.FileUploadException;
import com.team.jjan.user.exception.NoSuchEmailException;
import com.team.jjan.user.exception.NoSuchNicknameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountException;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(NoSuchEmailException.class)
    public ResponseEntity<ResponseMessage> noSuchEmailError(NoSuchEmailException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseMessage.of(ResponseCode.AUTHENTICATION_FAIL , e.getMessage()));
    }

    @ExceptionHandler(NoSuchNicknameException.class)
    public ResponseEntity<ResponseMessage> noSuchNicknameError(NoSuchNicknameException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseMessage.of(ResponseCode.AUTHENTICATION_FAIL , e.getMessage()));
    }

    @ExceptionHandler({FileUploadException.class , AccountException.class})
    public ResponseEntity<ResponseMessage> fileConversionError(FileUploadException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.of(ResponseCode.REQUEST_FAIL , e.getMessage()));
    }

    @ExceptionHandler(NoSuchPartyException.class)
    public ResponseEntity<ResponseMessage> noSuchPartyException(NoSuchPartyException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.of(ResponseCode.REQUEST_FAIL , e.getMessage()));
    }
}
