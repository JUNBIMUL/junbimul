package com.junbimul.service;

import com.junbimul.common.ConstProperties;
import com.junbimul.domain.Board;
import com.junbimul.domain.Comment;
import com.junbimul.domain.User;
import com.junbimul.dto.request.CommentDeleteRequestDto;
import com.junbimul.dto.request.CommentModifyRequestDto;
import com.junbimul.dto.request.CommentRequestDto;
import com.junbimul.dto.response.CommentDeleteResponseDto;
import com.junbimul.dto.response.CommentModifyResponseDto;
import com.junbimul.dto.response.CommentWriteResponseDto;
import com.junbimul.error.exception.BoardApiException;
import com.junbimul.error.exception.CommentApiException;
import com.junbimul.error.exception.UserApiException;
import com.junbimul.error.model.BoardErrorCode;
import com.junbimul.error.model.CommentErrorCode;
import com.junbimul.error.model.UserErrorCode;
import com.junbimul.repository.BoardRepository;
import com.junbimul.repository.CommentRepository;
import com.junbimul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@EnableConfigurationProperties(ConstProperties.class)
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ConstProperties constProperties;

    public CommentWriteResponseDto registComment(CommentRequestDto commentRequestDto) {
        commentContentLengthCheck(commentRequestDto.getContent());
        Board findBoard = boardRepository.findById(commentRequestDto.getBoardId());
        boardNullAndDeletedCheck(findBoard);
        User findUser = userRepository.findById(commentRequestDto.getUserId());
        userNullCheck(findUser);

        Comment comment = Comment.builder()
                .board(findBoard)
                .user(findUser)
                .content(commentRequestDto.getContent())
                .build();

        Long commentId = commentRepository.save(comment);


        return CommentWriteResponseDto.builder()
                .commentId(commentId)
                .build();
    }


    public CommentModifyResponseDto modifyComment(CommentModifyRequestDto commentModifyRequestDto) {
        Comment findComment = commentRepository.findById(commentModifyRequestDto.getCommentId());
        commentNullCheck(findComment);
        User findUser = userRepository.findById(commentModifyRequestDto.getUserId());
        userNullCheck(findUser);
        commentUserIdCheck(findComment, findUser);
        commentContentLengthCheck(commentModifyRequestDto.getContent());
        findComment.modifyContent(commentModifyRequestDto.getContent());
        return CommentModifyResponseDto.builder()
                .commentId(findComment.getId())
                .build();

    }


    public CommentDeleteResponseDto deleteComment(CommentDeleteRequestDto commentDeleteRequestDto) {
        Comment findComment = commentRepository.findById(commentDeleteRequestDto.getCommentId());
        commentNullCheck(findComment);
        User findUser = userRepository.findById(commentDeleteRequestDto.getUserId());
        userNullCheck(findUser);
        commentUserIdCheck(findComment, findUser);
        findComment.deleteComment();

        return CommentDeleteResponseDto.builder()
                .commentId(findComment.getId())
                .build();
    }

    public void commentContentLengthCheck(String content) {
        if (content.length() > constProperties.getCommentContentLength()) {
            throw new CommentApiException(CommentErrorCode.COMMENT_CONTENT_LENGTH_OVER);
        }
        if (content.length() == 0) {
            throw new CommentApiException(CommentErrorCode.COMMENT_CONTENT_LENGTH_ZERO);
        }
    }

    public void commentNullCheck(Comment findComment) {
        if (findComment == null) {
            throw new CommentApiException(CommentErrorCode.COMMENT_ID_NOT_FOUND);
        }
    }

    public void commentUserIdCheck(Comment findComment, User findUser) {
        if (findComment.getUser().getId() != findUser.getId()) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_MATCH);
        }
    }
    public void userNullCheck(User findUser) {
        if (findUser == null) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_FOUND);
        }
    }

    public void boardNullAndDeletedCheck(Board findBoard) {
        if (findBoard == null) {
            throw new BoardApiException(BoardErrorCode.BOARD_NOT_FOUND);
        }
        if (findBoard.getDeletedAt() != null) {
            throw new BoardApiException(BoardErrorCode.BOARD_DELETED);
        }
    }




}
