package jjan_back_renewal.common;

import jjan_back_renewal.common.ResponseMessage;
import jjan_back_renewal.party.exception.NoSuchPartyException;
import jjan_back_renewal.upload.FileUploadException;
import jjan_back_renewal.user.exception.NoSuchEmailException;
import jjan_back_renewal.user.exception.NoSuchNicknameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static jjan_back_renewal.common.ResponseCode.AUTHENTICATION_FAIL;
import static jjan_back_renewal.common.ResponseCode.REQUEST_FAIL;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(NoSuchEmailException.class)
    public ResponseEntity<ResponseMessage> noSuchEmailError(NoSuchEmailException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseMessage.of(AUTHENTICATION_FAIL));
    }

    @ExceptionHandler(NoSuchNicknameException.class)
    public ResponseEntity<ResponseMessage> noSuchNicknameError(NoSuchNicknameException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseMessage.of(AUTHENTICATION_FAIL));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ResponseMessage> fileConversionError(FileUploadException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.of(REQUEST_FAIL));
    }

    @ExceptionHandler(NoSuchPartyException.class)
    public ResponseEntity<ResponseMessage> noSuchPartyException(NoSuchPartyException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage.of(REQUEST_FAIL));
    }
}
