package com.junbimul.controller;

import com.junbimul.domain.Board;
import com.junbimul.dto.CombinedDto;
import com.junbimul.dto.request.BoardRequestDto;
import com.junbimul.dto.request.UserRequestDto;
import com.junbimul.dto.response.BoardDetailResponseDto;
import com.junbimul.dto.response.BoardResponseDto;
import com.junbimul.service.BoardService;
import com.junbimul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/board")
    public ResponseEntity<?> writeBoard(@RequestBody CombinedDto combinedDto) {
        BoardRequestDto boardDto = combinedDto.getBoardRequestDto();
        UserRequestDto userDto = combinedDto.getUserRequestDto();
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .user(userService.getUser(userDto.getId())).build();
        boardService.registBoard(board);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/board")
    public ResponseEntity<List<BoardResponseDto>> list() {
        List<BoardResponseDto> boards = boardService.findBoards();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<BoardDetailResponseDto> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getBoardDetailById(id));
    }
}