package com.junbimul.error.exception;

import com.junbimul.error.model.ErrorCode;
import lombok.Getter;

@Getter
public class InterceptorException extends RuntimeException{
    private final ErrorCode errorCode;

    public InterceptorException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
