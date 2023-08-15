package com.junbimul.exception.user;

import com.junbimul.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserNicknameDuplicateException extends RuntimeException {
    private ErrorCode errorCode;

    public UserNicknameDuplicateException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
