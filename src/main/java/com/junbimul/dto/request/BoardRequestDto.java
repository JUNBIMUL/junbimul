package com.junbimul.dto.request;

import com.junbimul.domain.Comment;
import com.junbimul.domain.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BoardRequestDto {
    private Long id;
    private String title;
    private String content;

    private Long viewCnt;

    private User user;

}
