package com.junbimul.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode {

    BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST, "BOARD_ERROR_400", "해당 게시글 ID 없음"),
    BOARD_TITLE_LENGTH_OVER(HttpStatus.BAD_REQUEST, "BOARD_ERROR_400", "제목 글자 개수 초과(max : 30)"),
    BOARD_TITLE_LENGTH_ZERO(HttpStatus.BAD_REQUEST, "BOARD_ERROR_400", "제목 입력 필수"),
    BOARD_CONTENT_LENGTH_OVER(HttpStatus.BAD_REQUEST, "COMMENT_ERROR_404", "게시글 글자 개수 초과(max : 200)"),
    BOARD_CONTENT_LENGTH_ZERO(HttpStatus.BAD_REQUEST, "BOARD_ERROR_400", "게시글 내용 입력 필수"),
    BOARD_DELETED(HttpStatus.BAD_REQUEST, "BOARD_ERROR_400", "게시글 삭제됨");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
