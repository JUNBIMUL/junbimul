package com.junbimul.controller;

import com.junbimul.dto.request.CommentDeleteRequestDto;
import com.junbimul.dto.request.CommentModifyRequestDto;
import com.junbimul.dto.request.CommentRequestDto;
import com.junbimul.dto.response.CommentDeleteResponseDto;
import com.junbimul.dto.response.CommentModifyResponseDto;
import com.junbimul.dto.response.CommentWriteResponseDto;
import com.junbimul.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "댓글")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    @Operation(summary = "댓글 작성")
    public ResponseEntity<CommentWriteResponseDto> writeComment(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginId");
        return ResponseEntity.ok(commentService.registComment(commentRequestDto, loginId));
    }

    @PutMapping("/comment")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<CommentModifyResponseDto> modifyComment(@RequestBody CommentModifyRequestDto commentModifyRequestDto, HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginId");
        return ResponseEntity.ok(commentService.modifyComment(commentModifyRequestDto, loginId));
    }

    @DeleteMapping("/comment")
    @Operation(summary = "댓글 삭제")
    private ResponseEntity<CommentDeleteResponseDto> deleteComment(@RequestBody CommentDeleteRequestDto commentDeleteRequestDto, HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginId");
        return ResponseEntity.ok(commentService.deleteComment(commentDeleteRequestDto, loginId));
    }
}
