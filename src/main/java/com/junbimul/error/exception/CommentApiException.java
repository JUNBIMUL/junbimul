package com.junbimul.error.exception;

import com.junbimul.error.model.ErrorCode;
import lombok.Getter;

@Getter
public class CommentApiException extends RuntimeException {
    private final ErrorCode errorCode;

    public CommentApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
