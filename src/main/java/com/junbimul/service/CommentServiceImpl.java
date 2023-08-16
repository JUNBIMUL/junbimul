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
import com.junbimul.error.model.CommentErrorCode;
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
            throw new CommentApiException(CommentErrorCode.CONTENT_LENGTH_OVER);
        }
        // 여기서 하나씩 뽑아
        Long boardId = commentRequestDto.getBoardId();
        Long userId = commentRequestDto.getUserId();
        Board findBoard = boardRepository.findOne(boardId);
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
        if (commentModifyRequestDto.getContent().length() > 200) {
            throw new CommentApiException(CommentErrorCode.CONTENT_LENGTH_OVER);
        }
        Comment findComment = commentRepository.findById(commentModifyRequestDto.getCommentId());
        // 추후 : null값 확인(DB에서 null 허용 안 하는지), 길이(범위 맞는지), 유저 확인, findByID했을 때 안 되면.. 예외처리
        findComment.modifyContent(commentModifyRequestDto.getContent());


        return CommentModifyResponseDto.builder()
                .commentId(findComment.getId())
                .build();

    }

    public CommentDeleteResponseDto deleteComment(CommentDeleteRequestDto commentDeleteRequestDto) {
        Comment findComment = commentRepository.findById(commentDeleteRequestDto.getCommentId());
        findComment.deleteComment();

        return CommentDeleteResponseDto.builder()
                .commentId(findComment.getId())
                .build();
    }
}
