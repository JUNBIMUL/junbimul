package com.junbimul.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    CONTENT_LENGTH_OVER(HttpStatus.BAD_REQUEST, "COMMENT_ERROR_404", "댓글 글자 게수 초과(max : 200)");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
