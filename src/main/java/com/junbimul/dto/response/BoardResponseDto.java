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
public class BoardResponseDto {
    private Long boardId;
    private String title;
    private Long viewCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String nickname;

}