package com.junbimul.service;

import com.junbimul.domain.Board;
import com.junbimul.domain.Comment;
import com.junbimul.domain.User;
import com.junbimul.dto.request.CommentModifyRequestDto;
import com.junbimul.dto.request.CommentRequestDto;
import com.junbimul.repository.BoardRepository;
import com.junbimul.repository.CommentRepository;
import com.junbimul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Long registComment(CommentRequestDto commentRequestDto) {
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
        return commentRepository.save(comment);
    }

    public Long modifyComment(CommentModifyRequestDto commentModifyRequestDto) {
        Comment findComment = commentRepository.findById(commentModifyRequestDto.getCommentId());
        // 추후 : null값 확인(DB에서 null 허용 안 하는지), 길이(범위 맞는지), 유저 확인, findByID했을 때 안 되면.. 예외처리
        findComment.modifyContent(commentModifyRequestDto.getContent());
        return findComment.getId();
    }

    public Long deleteComment(CommentModifyRequestDto commentModifyRequestDto) {
        Comment findComment = commentRepository.findById(commentModifyRequestDto.getCommentId());
        findComment.deleteComment();
        return findComment.getId();
    }
}
