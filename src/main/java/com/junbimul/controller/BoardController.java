package com.junbimul.controller;

import com.junbimul.domain.Board;
import com.junbimul.dto.CombinedDto;
import com.junbimul.dto.request.BoardRequestDto;
import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.BoardDeleteResponseDto;
import com.junbimul.dto.response.BoardDetailResponseDto;
import com.junbimul.dto.response.BoardModifyResponseDto;
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
        UserSignupRequestDto userDto = combinedDto.getUserRequestDto();
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .viewCnt(0L)
                .user(userService.getUser(userDto.getUserId())).build();
        boardService.registerBoard(board);
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

    @PutMapping("/board")
    public ResponseEntity<BoardModifyResponseDto> modifyBoard(@RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.ok(BoardModifyResponseDto.builder()
                .boardId(boardService.modifyBoard(boardRequestDto))
                .build());
    }

    @DeleteMapping("/board")
    public ResponseEntity<BoardDeleteResponseDto> deleteBoard(@RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.ok(BoardDeleteResponseDto.builder()
                .boardId(boardService.deleteBoard(boardRequestDto))
                .build());
    }
}