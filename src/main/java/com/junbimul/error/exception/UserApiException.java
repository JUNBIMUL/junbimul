package com.junbimul.error.exception;

import com.junbimul.error.model.ErrorCode;
import lombok.Getter;

@Getter
public class UserApiException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
