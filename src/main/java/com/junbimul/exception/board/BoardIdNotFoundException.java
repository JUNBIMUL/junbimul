package com.junbimul.exception.board;

import com.junbimul.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BoardIdNotFoundException extends RuntimeException {
    private ErrorCode errorCode;

    public BoardIdNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
