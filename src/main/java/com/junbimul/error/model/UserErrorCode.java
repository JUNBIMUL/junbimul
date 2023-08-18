package com.junbimul.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "해당 사용자 ID 없음"),
    USER_ID_NOT_MATCH(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "해당 글에 대한 접근 권한이 없습니다"),
    USER_DUPLICATE_EXCEPTION(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "닉네임 중복"),
    USER_NICKNAME_LENGTH_OVER(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "닉네임 길이 초과(max : 30"),
    USER_NICKNAME_LENGTH_ZERO(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "닉네임 입력 필수"),
    USER_USERID_LENGTH_OVER(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "아이디 길이 초과(max : 50"),
    USER_USERID_LENGTH_ZERO(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "아이디 입력 필수"),

    USER_USERID_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "해당 ID의 사용자는 없습니다"),
    USER_PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "USER_ERROR_400", "비밀번호가 일치하지 않습니다");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
