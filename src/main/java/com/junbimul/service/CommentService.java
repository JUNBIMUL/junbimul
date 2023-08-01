package com.junbimul.service;

import com.junbimul.domain.Board;
import com.junbimul.domain.Comment;
import com.junbimul.domain.User;
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
}
