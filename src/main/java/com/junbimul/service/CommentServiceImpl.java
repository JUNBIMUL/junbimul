package com.junbimul.service;

import com.junbimul.domain.Board;
import com.junbimul.domain.Comment;
import com.junbimul.domain.User;
import com.junbimul.dto.request.CommentDeleteRequestDto;
import com.junbimul.dto.request.CommentModifyRequestDto;
import com.junbimul.dto.request.CommentRequestDto;
import com.junbimul.dto.response.CommentDeleteResponseDto;
import com.junbimul.dto.response.CommentModifyResponseDto;
import com.junbimul.dto.response.CommentWriteResponseDto;
import com.junbimul.error.exception.CommentApiException;
import com.junbimul.error.exception.UserApiException;
import com.junbimul.error.model.CommentErrorCode;
import com.junbimul.error.model.UserErrorCode;
import com.junbimul.repository.BoardRepository;
import com.junbimul.repository.CommentRepository;
import com.junbimul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public CommentWriteResponseDto registComment(CommentRequestDto commentRequestDto) {
        if (commentRequestDto.getContent().length() > 200) {
            throw new CommentApiException(CommentErrorCode.COMMENT_CONTENT_LENGTH_OVER);
        }
        if (commentRequestDto.getContent().length() == 0) {
            throw new CommentApiException(CommentErrorCode.COMMENT_CONTENT_LENGTH_ZERO);
        }
        // 여기서 하나씩 뽑아
        Long boardId = commentRequestDto.getBoardId();
        Long userId = commentRequestDto.getUserId();
        Board findBoard = boardRepository.findById(boardId);
        User findUser = userRepository.findById(userId);

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
        if (findComment == null) {
            throw new CommentApiException(CommentErrorCode.COMMENT_ID_NOT_FOUND);
        }
        User findUser = userRepository.findById(commentModifyRequestDto.getCommentId());
        if (findUser == null) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_FOUND);
        }
        if (findComment.getUser().getId() != findUser.getId()) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_MATCH);
        }
        if (commentModifyRequestDto.getContent().length() > 200) {
            throw new CommentApiException(CommentErrorCode.COMMENT_CONTENT_LENGTH_OVER);
        }
        if (commentModifyRequestDto.getContent().length() == 0) {
            throw new CommentApiException(CommentErrorCode.COMMENT_CONTENT_LENGTH_ZERO);
        }
        findComment.modifyContent(commentModifyRequestDto.getContent());
        return CommentModifyResponseDto.builder()
                .commentId(findComment.getId())
                .build();

    }

    public CommentDeleteResponseDto deleteComment(CommentDeleteRequestDto commentDeleteRequestDto) {
        Comment findComment = commentRepository.findById(commentDeleteRequestDto.getCommentId());
        if (findComment == null) {
            throw new CommentApiException(CommentErrorCode.COMMENT_ID_NOT_FOUND);
        }
        User findUser = userRepository.findById(commentDeleteRequestDto.getUserId());
        if (findUser == null) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_FOUND);
        }
        if (findComment.getUser().getId() != findUser.getId()) {
            throw new UserApiException(UserErrorCode.USER_ID_NOT_MATCH);
        }
        findComment.deleteComment();

        return CommentDeleteResponseDto.builder()
                .commentId(findComment.getId())
                .build();
    }
}
