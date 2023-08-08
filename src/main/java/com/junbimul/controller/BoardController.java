package com.junbimul.controller;

import com.junbimul.dto.CombinedDto;
import com.junbimul.dto.request.BoardRequestDto;
import com.junbimul.dto.request.UserRequestDto;
import com.junbimul.dto.response.*;
import com.junbimul.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<?> writeBoard(@RequestBody CombinedDto combinedDto) {
        BoardRequestDto boardDto = combinedDto.getBoardRequestDto();
        UserRequestDto userDto = combinedDto.getUserRequestDto();

        BoardWriteResponseDto boardWriteResponseDto = boardService.registerBoard(boardDto, userDto);
        return ResponseEntity.ok(boardWriteResponseDto);
    }

    @GetMapping("/board")
    public ResponseEntity<BoardListResponseDto> getBoardList() {
        return ResponseEntity.ok(boardService.findBoards());
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<BoardDetailResponseDto> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getBoardDetailById(id));
    }

    @PutMapping("/board")
    public ResponseEntity<BoardModifyResponseDto> modifyBoard(@RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.ok(boardService.modifyBoard(boardRequestDto));
    }

    @DeleteMapping("/board")
    public ResponseEntity<BoardDeleteResponseDto> deleteBoard(@RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.ok(boardService.deleteBoard(boardRequestDto));
    }
}