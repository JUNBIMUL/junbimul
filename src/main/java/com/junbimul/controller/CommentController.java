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

@RestController
@Tag(name = "댓글")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    @Operation(summary = "댓글 작성")
    public ResponseEntity<CommentWriteResponseDto> writeComment(@RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok(commentService.registComment(commentRequestDto));
    }

    @PutMapping("/comment")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<CommentModifyResponseDto> modifyComment(@RequestBody CommentModifyRequestDto commentModifyRequestDto) {
        return ResponseEntity.ok(commentService.modifyComment(commentModifyRequestDto));
    }

    @DeleteMapping("/comment")
    @Operation(summary = "댓글 삭제")
    private ResponseEntity<CommentDeleteResponseDto> deleteComment(@RequestBody CommentDeleteRequestDto commentDeleteRequestDto) {
        return ResponseEntity.ok(commentService.deleteComment(commentDeleteRequestDto));
    }
}
