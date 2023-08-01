package com.junbimul.service;

import com.junbimul.domain.Board;
import com.junbimul.domain.Comment;
import com.junbimul.dto.response.BoardDetailResponseDto;
import com.junbimul.dto.response.BoardResponseDto;
import com.junbimul.dto.response.CommentResponseDto;
import com.junbimul.repository.BoardRepository;
import com.junbimul.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 게시글 등록
    public Long registBoard(Board board) {
        return boardRepository.save(board);
    }

    // 게시글 전체 가져오기
    public List<BoardResponseDto> findBoards() {
        return boardRepository.findAll()
                .stream()
                .map(board -> BoardResponseDto.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .viewCnt(board.getViewCnt())
                        .createdAt(board.getCreatedAt())
                        .updatedAt(board.getUpdatedAt())
                        .nickname(board.getUser().getNickname())
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    // 게시글 하나 가져오기
    public BoardDetailResponseDto getBoardDetailById(Long id) {
        Board findBoard = boardRepository.findOne(id);
        List<Comment> commentsByBoardId = commentRepository.findCommentsByBoard(id);
        System.out.println("aaaaaa");
        for (Comment comment : commentsByBoardId) {
            System.out.println(comment.getId());
        }

        return BoardDetailResponseDto.builder()
                .title(findBoard.getTitle())
                .content(findBoard.getContent())
                .viewCnt(findBoard.getViewCnt())
                .createdAt(findBoard.getCreatedAt())
                .updatedAt(findBoard.getUpdatedAt())
                .nickname(findBoard.getUser().getNickname())
                .commentList(commentsByBoardId.stream()
                        .map(comment -> CommentResponseDto.builder()
                                .id(comment.getId())
                                .userName(comment.getUser().getNickname())
                                .updatedAt(comment.getUpdatedAt())
                                .content(comment.getContent())
                                .build()).collect(Collectors.toUnmodifiableList()))
                .build();
    }

}