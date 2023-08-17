package com.junbimul.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BoardModifyRequestDto {
    private Long boardId;
    private Long userId;
    private String title;
    private String content;
}
