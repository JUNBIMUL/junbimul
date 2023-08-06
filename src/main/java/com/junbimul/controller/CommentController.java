package com.junbimul.controller;

import com.junbimul.dto.request.CommentModifyRequestDto;
import com.junbimul.dto.request.CommentRequestDto;
import com.junbimul.dto.response.CommentDeleteResponseDto;
import com.junbimul.dto.response.CommentModifyResponseDto;
import com.junbimul.dto.response.CommentWriteDto;
import com.junbimul.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> writeComment(@RequestBody CommentRequestDto commentRequestDto) {
        Long commentId = commentService.registComment(commentRequestDto);
        return ResponseEntity.ok(CommentWriteDto.builder()
                .commentId(commentId)
                .build());
    }

    @PutMapping("/comment")
    public ResponseEntity<?> modifyComment(@RequestBody CommentModifyRequestDto commentModifyRequestDto) {
        Long commentId = commentService.modifyComment(commentModifyRequestDto);
        return ResponseEntity.ok(CommentModifyResponseDto.builder()
                .commentId(commentId)
                .build());
    }

    @DeleteMapping("/comment")
    private ResponseEntity<?> deleteComment(@RequestBody CommentModifyRequestDto commentModifyRequestDto) {
        Long commentId = commentService.deleteComment(commentModifyRequestDto);
        return ResponseEntity.ok(CommentDeleteResponseDto.builder()
                .commentId(commentId)
                .build());
    }
}
