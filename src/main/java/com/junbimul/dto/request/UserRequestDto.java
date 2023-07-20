package com.junbimul.dto.request;

import com.junbimul.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
