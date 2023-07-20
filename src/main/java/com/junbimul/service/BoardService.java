package com.junbimul.service;

import com.junbimul.domain.Board;
import com.junbimul.domain.User;
import com.junbimul.dto.response.BoardResponseDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.repository.BoardRepository;
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
                        .userResponseDto(
                                UserResponseDto.builder()
                                        .nickname(board.getUser().getNickname())
                                        .build())
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    // 게시글 하나 가져오기
    public BoardResponseDto getBoardById(Long id) {
        Board findBoard = boardRepository.findOne(id);

        return BoardResponseDto.builder()
                .title(findBoard.getTitle())
                .content(findBoard.getContent())
                .viewCnt(findBoard.getViewCnt())
                .createdAt(findBoard.getCreatedAt())
                .updatedAt(findBoard.getUpdatedAt())
                .userResponseDto(
                        UserResponseDto.builder()
                                .nickname(findBoard.getUser().getNickname())
                                .build())
                .build();
    }

}


