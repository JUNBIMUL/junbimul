package com.junbimul.error;

import com.junbimul.error.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BoardApiException.class)
    public ResponseEntity<ErrorResponse> boardApiException(BoardApiException e) {
        log.error("BoardApiException", e);
        ErrorResponse response = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new ErrorResponse(e.getErrorCode()));
    }


    @ExceptionHandler(UserApiException.class)
    public ResponseEntity<ErrorResponse> userApiException(UserApiException e) {
        log.error("UserApiException", e);
        ErrorResponse response = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new ErrorResponse(e.getErrorCode()));
    }

    @ExceptionHandler(CommentApiException.class)
    public ResponseEntity<ErrorResponse> commentApiException(UserApiException e) {
        log.error("CommentApiException", e);
        ErrorResponse response = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new ErrorResponse(e.getErrorCode()));
    }

}