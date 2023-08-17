package com.junbimul.controller;

import com.junbimul.dto.CombinedDto;
import com.junbimul.dto.request.BoardModifyRequestDto;
import com.junbimul.dto.request.BoardRequestDto;
import com.junbimul.dto.request.UserRequestDto;
import com.junbimul.dto.response.*;
import com.junbimul.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "게시글")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    @Operation(summary = "게시글 등록")
    public ResponseEntity<?> writeBoard(@RequestBody CombinedDto combinedDto) {
        BoardRequestDto boardDto = combinedDto.getBoardRequestDto();
        UserRequestDto userDto = combinedDto.getUserRequestDto();

        BoardWriteResponseDto boardWriteResponseDto = boardService.registerBoard(boardDto, userDto);

        return ResponseEntity.ok(boardWriteResponseDto);
    }

    @GetMapping("/board")
    @Operation(summary = "게시글 전체 조회")
    public ResponseEntity<BoardListResponseDto> getBoardList() {
        return ResponseEntity.ok(boardService.findBoards());
    }

    @GetMapping("/board/{id}")
    @Operation(summary = "게시글 상세 조회")
    public ResponseEntity<BoardDetailResponseDto> getBoard(@PathVariable Long id) {
        BoardDetailResponseDto boardDetailById = boardService.getBoardDetailById(id);
        return ResponseEntity.ok(boardDetailById);
    }

    @PutMapping("/board")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<BoardModifyResponseDto> modifyBoard(@RequestBody BoardModifyRequestDto boardModifyRequestDto) {
        return ResponseEntity.ok(boardService.modifyBoard(boardModifyRequestDto));
    }

    @DeleteMapping("/board")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<BoardDeleteResponseDto> deleteBoard(@RequestBody BoardRequestDto boardRequestDto) {
        return ResponseEntity.ok(boardService.deleteBoard(boardRequestDto));
    }
}