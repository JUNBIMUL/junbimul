package com.junbimul.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CommentModifyRequestDto {
    private Long commentId;
    private Long userId;
    private String content;
}
