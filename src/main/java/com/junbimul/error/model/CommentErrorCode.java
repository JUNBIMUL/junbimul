package com.junbimul.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    COMMENT_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMENT_ERROR_400", "해당 댓글 ID 없음"),
    COMMENT_CONTENT_LENGTH_OVER(HttpStatus.BAD_REQUEST, "COMMENT_ERROR_400", "댓글 글자 개수 초과(max : 200)"),
    COMMENT_CONTENT_LENGTH_ZERO(HttpStatus.BAD_REQUEST, "COMMENT_ERROR_400", "댓글 글자 입력 필요"),

    CONTENT_DELETED(HttpStatus.BAD_REQUEST, "COMMENT_ERROR_400", "댓글 삭제됨");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
