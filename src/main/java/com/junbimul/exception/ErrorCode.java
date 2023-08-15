package com.junbimul.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public enum ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_ERROR_404", "PAGE NOT FOUND"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_ERROR_500", "INTERNAL SERVER ERROR"),
    BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST, "BOARD_ERROR_400", "BOARD_ID NOT FOUND"),

    USER_DUPLICATE_EXCEPTION(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "USER_NICKNAME DUPLICATED");

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
