package com.junbimul.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private HttpStatus httpStatus;
    private String message;
    private String code;

    public ErrorResponse(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getErrorCode();
    }
}
