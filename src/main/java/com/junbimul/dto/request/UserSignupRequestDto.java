package com.junbimul.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserSignupRequestDto {
    private String nickname;
}