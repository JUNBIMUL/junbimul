package com.junbimul.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private LocalDateTime updatedAt;
    private String userName;
}
