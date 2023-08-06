package com.junbimul.controller;

import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity signup(@RequestBody UserSignupRequestDto userDto) {
        UserSignupRequestDto user = UserSignupRequestDto.builder()
                .nickname(userDto.getNickname())
                .build();
        Long userId = userService.join(user);


        // 여기서 토큰 관련 처리
        return ResponseEntity.ok(UserResponseDto.builder()
                .nickname(userDto.getNickname())
                .userId(userId)
                .build());
    }

}
