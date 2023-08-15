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
public class UserResponseDto {
    private Long userId;
    private String nickname;
    private LocalDateTime createdAt;
}
