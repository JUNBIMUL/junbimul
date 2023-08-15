package com.junbimul.exception.board;

import com.junbimul.exception.ErrorCode;
import com.junbimul.exception.ErrorResponse;
import com.junbimul.exception.user.UserNicknameDuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(BoardIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> boardIdNotFoundException(BoardIdNotFoundException e){
        log.error("BoardIdNotFoundException",e);
        ErrorResponse response = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new ErrorResponse(e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        log.error("handleException",ex);
        ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler(UserNicknameDuplicateException.class)
    public ResponseEntity<ErrorResponse> userNicknameDuplicateException(UserNicknameDuplicateException e) {
        log.error("UserNicknameDuplicateException", e);
        ErrorResponse response = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new ErrorResponse(e.getErrorCode()));
    }
}