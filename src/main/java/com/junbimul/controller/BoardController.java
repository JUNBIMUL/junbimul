package com.junbimul.controller;

import com.junbimul.dto.request.BoardDeleteRequestDto;
import com.junbimul.dto.request.BoardModifyRequestDto;
import com.junbimul.dto.request.BoardWriteRequestDto;
import com.junbimul.dto.response.*;
import com.junbimul.service.BoardService;
import com.junbimul.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "게시글")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final JwtUtil jwtUtil;

    @PostMapping("/board")
    @Operation(summary = "게시글 등록")
    public ResponseEntity<?> writeBoard(@RequestBody BoardWriteRequestDto boardWriteRequestDto, HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginId");
        BoardWriteResponseDto boardWriteResponseDto = boardService.registerBoard(boardWriteRequestDto, loginId);

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
    public ResponseEntity<BoardModifyResponseDto> modifyBoard(@RequestBody BoardModifyRequestDto boardModifyRequestDto, HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginId");
        return ResponseEntity.ok(boardService.modifyBoard(boardModifyRequestDto, loginId));
    }

    @DeleteMapping("/board")
    @Operation(summary = "게시글 삭제")
    public ResponseEntity<BoardDeleteResponseDto> deleteBoard(@RequestBody BoardDeleteRequestDto boardDeleteRequestDto, HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginId");
        return ResponseEntity.ok(boardService.deleteBoard(boardDeleteRequestDto, loginId));
    }
}