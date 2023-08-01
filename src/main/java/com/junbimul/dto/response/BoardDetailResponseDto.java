package com.junbimul.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class BoardDetailResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long viewCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String nickname;
    private List<CommentResponseDto> commentList;
}
