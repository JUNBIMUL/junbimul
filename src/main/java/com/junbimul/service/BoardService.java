package com.junbimul.service;

import com.junbimul.domain.Board;
import com.junbimul.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 등록
    public Long registBoard(Board board) {
        return boardRepository.save(board);
    }

    // 게시글 전체 가져오기
    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    // 게시글 하나 가져오기
    public Board findOne(Long id) {
        return boardRepository.findOne(id);
    }

}
