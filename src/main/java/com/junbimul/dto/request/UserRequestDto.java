package com.junbimul.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserRequestDto {
    private Long userId;
    private String nickname;
}