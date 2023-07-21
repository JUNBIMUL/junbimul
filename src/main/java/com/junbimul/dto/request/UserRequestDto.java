package com.junbimul.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserRequestDto {
    private Long id;
    private String nickname;
    private LocalDateTime createdAt;
    public UserRequestDto(String nickname) {
        this.nickname = nickname;
    }
}