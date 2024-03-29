package com.team.jjan.common;

import com.team.jjan.jwt.exception.AuthenticationException;
import com.team.jjan.party.exception.NoSuchPartyException;
import com.team.jjan.upload.exception.FileUploadException;
import com.team.jjan.user.exception.NoSuchEmailException;
import com.team.jjan.user.exception.NoSuchNicknameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountException;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler({NoSuchEmailException.class , AuthenticationException.class})
    public ResponseEntity<ResponseMessage> noSuchEmailError(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseMessage.of(ResponseCode.AUTHENTICATION_FAIL , e.getMessage()));
    }

    @ExceptionHandler(NoSuchNicknameException.class)
    public ResponseEntity<ResponseMessage> noSuchNicknameError(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseMessage.of(ResponseCode.AUTHENTICATION_FAIL , e.getMessage()));
    }

    @ExceptionHandler({FileUploadException.class , AccountException.class , UsernameNotFoundException.class})
    public ResponseEntity<ResponseMessage> fileConversionError(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.of(ResponseCode.REQUEST_FAIL , e.getMessage()));
    }

    @ExceptionHandler(NoSuchPartyException.class)
    public ResponseEntity<ResponseMessage> noSuchPartyException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.of(ResponseCode.REQUEST_FAIL , e.getMessage()));
    }

}
