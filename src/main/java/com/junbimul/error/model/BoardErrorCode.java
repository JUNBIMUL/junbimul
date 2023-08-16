package com.junbimul.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode {

    BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST, "BOARD_ERROR_400", "BOARD_ID NOT FOUND"),
    TITLE_LENGTH_OVER(HttpStatus.BAD_REQUEST, "COMMENT_ERROR_404", "댓글 글자 게수 초과(max : 30)"),
    CONTENT_LENGTH_OVER(HttpStatus.BAD_REQUEST, "COMMENT_ERROR_404", "댓글 글자 게수 초과(max : 200)");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
