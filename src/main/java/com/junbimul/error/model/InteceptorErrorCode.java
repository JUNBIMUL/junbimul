package com.junbimul.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InteceptorErrorCode implements ErrorCode{

    UNAUTHORIZED(HttpStatus.BAD_REQUEST, "INTERCEPTOR_ERROR_400", "토큰 인증 X"),
    MALFORMED(HttpStatus.BAD_REQUEST, "INTERCEPTOR_ERROR_400", "토큰 형태 이상"),
    EXPIRED(HttpStatus.BAD_REQUEST, "INTERCEPTOR_ERROR_400", "토큰 만료");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
