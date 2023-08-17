package com.junbimul.error.exception;

import com.junbimul.error.model.ErrorCode;
import lombok.Getter;

@Getter
public class BoardApiException extends RuntimeException {
    private final ErrorCode errorCode;

    public BoardApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
