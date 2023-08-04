package com.junbimul.service;

import com.junbimul.domain.Board;
import com.junbimul.domain.Comment;
import com.junbimul.dto.request.BoardRequestDto;
import com.junbimul.dto.response.BoardDetailResponseDto;
import com.junbimul.dto.response.BoardResponseDto;
import com.junbimul.dto.response.CommentResponseDto;
import com.junbimul.repository.BoardRepository;
import com.junbimul.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 게시글 등록
    public Long registerBoard(Board board) {
        return boardRepository.save(board);
    }

    // 게시글 전체 가져오기
    public List<BoardResponseDto> findBoards() {
        return boardRepository.findAll()
                .stream()
                .filter(board -> board.getDeletedAt() == null)
                .map(board -> BoardResponseDto.builder()
                        .boardId(board.getId())
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
        // 요청이 들어올 때 삭제된 건지, 아닌지 판단해야할듯
        Board findBoard = boardRepository.findOne(id);
        // 이제 여기서 문제가 생김, 예외 처리 해야하는 부분
        findBoard.updateViewCount();
        List<Comment> commentsByBoardId = commentRepository.findCommentsByBoard(id).stream().filter(c -> c.getDeletedAt() == null).collect(Collectors.toUnmodifiableList());;
        return BoardDetailResponseDto.builder()
                .boardId(findBoard.getId())
                .title(findBoard.getTitle())
                .content(findBoard.getContent())
                .viewCnt(findBoard.getViewCnt())
                .createdAt(findBoard.getCreatedAt())
                .updatedAt(findBoard.getUpdatedAt())
                .nickname(findBoard.getUser().getNickname())
                .commentList(commentsByBoardId.stream()
                        .map(comment -> CommentResponseDto.builder()
                                .commentId(comment.getId())
                                .userName(comment.getUser().getNickname())
                                .updatedAt(comment.getUpdatedAt())
                                .content(comment.getContent())
                                .build())
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    public Long modifyBoard(BoardRequestDto boardRequestDto) {
        Long boardId = boardRequestDto.getBoardId();
        Board findBoard = boardRepository.findOne(boardId);
        String modifiedTitle = boardRequestDto.getTitle();
        String modifiedContent = boardRequestDto.getContent();
        findBoard.modify(modifiedTitle, modifiedContent, LocalDateTime.now());
        return boardId;
    }

    public Long deleteBoard(BoardRequestDto boardRequestDto) {
        Long boardId = boardRequestDto.getBoardId();
        Board findBoard = boardRepository.findOne(boardId);
        findBoard.delete(LocalDateTime.now());
        return boardId;
    }
}