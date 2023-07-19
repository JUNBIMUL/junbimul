package com.junbimul.controller;

import com.junbimul.domain.Board;
import com.junbimul.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/board/create")
    public Long createBoard(@RequestParam Board board) {
        return boardService.registBoard(board);
    }

    @RequestMapping("/board")
    public List<Board> selectAll() {
        return boardService.findBoards();
    }

    @RequestMapping("/board/{id}")
    public Board selectOne(@RequestParam Long id) {
        return boardService.findOne(id);
    }
}
