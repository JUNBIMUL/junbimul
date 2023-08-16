package com.junbimul.error.model;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();

    String getErrorCode();

    String getMessage();
}
