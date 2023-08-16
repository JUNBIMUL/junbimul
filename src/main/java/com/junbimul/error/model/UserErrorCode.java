package com.junbimul.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_DUPLICATE_EXCEPTION(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "USER_NICKNAME DUPLICATED"),
    USER_NICKNAME_LENGTH_OVER(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "USER_NICKNAME_LENGTH_OVER");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
