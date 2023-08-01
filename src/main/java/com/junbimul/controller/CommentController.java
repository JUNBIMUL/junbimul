package com.junbimul.controller;

import com.junbimul.dto.request.CommentRequestDto;
import com.junbimul.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> writeComment(@RequestBody CommentRequestDto commentRequestDto) {
        commentService.registComment(commentRequestDto);
        return ResponseEntity.ok().build();
    }
}
