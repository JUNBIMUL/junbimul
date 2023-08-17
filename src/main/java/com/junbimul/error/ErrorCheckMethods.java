package com.junbimul.error;

import com.junbimul.domain.Board;
import com.junbimul.domain.Comment;
import com.junbimul.domain.User;
import com.junbimul.error.exception.BoardApiException;
import com.junbimul.error.exception.CommentApiException;
import com.junbimul.error.exception.UserApiException;
import com.junbimul.error.model.BoardErrorCode;
import com.junbimul.error.model.CommentErrorCode;
import com.junbimul.error.model.UserErrorCode;

public class ErrorCheckMethods {

    public static void userNullCheck(User findUser) {
        if (findUser == null) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_FOUND);
        }
    }

    public static void boardNullAndDeletedCheck(Board findBoard) {
        if (findBoard == null) {
            throw new BoardApiException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        if (findBoard.getDeletedAt() != null) {
            throw new BoardApiException(BoardErrorCode.BOARD_DELETED);
        }
    }

    public static void userBoardNullCheckAndHasSameId(Board findBoard, User findUser) {
        userNullCheck(findUser);
        boardNullAndDeletedCheck(findBoard);

        if (findBoard.getUser().getId() != findUser.getId()) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_MATCH);
        }
    }

    public static void checkTitleContentLength(String title, String content) {
        if (title.length() > 30) {
            throw new BoardApiException(BoardErrorCode.BOARD_TITLE_LENGTH_OVER);
        }
        if (title.length() == 0) {
            throw new BoardApiException(BoardErrorCode.BOARD_TITLE_LENGTH_ZERO);
        }
        if (content.length() > 200) {
            throw new BoardApiException(BoardErrorCode.BOARD_CONTENT_LENGTH_OVER);
        }
        if (content.length() == 0) {
            throw new BoardApiException(BoardErrorCode.BOARD_CONTENT_LENGTH_ZERO);
        }
    }

    public static void commentContentLengthCheck(String content) {
        if (content.length() > 200) {
            throw new CommentApiException(CommentErrorCode.COMMENT_CONTENT_LENGTH_OVER);
        }
        if (content.length() == 0) {
            throw new CommentApiException(CommentErrorCode.COMMENT_CONTENT_LENGTH_ZERO);
        }
    }
    public static void commentNullCheck(Comment findComment) {
        if (findComment == null) {
            throw new CommentApiException(CommentErrorCode.COMMENT_ID_NOT_FOUND);
        }
    }

    public static void commentUserIdCheck(Comment findComment, User findUser) {
        if (findComment.getUser().getId() != findUser.getId()) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_MATCH);
        }
    }
}
