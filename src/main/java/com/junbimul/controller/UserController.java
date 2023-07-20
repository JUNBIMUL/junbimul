package com.junbimul.controller;

import com.junbimul.domain.User;
import com.junbimul.dto.request.UserRequestDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity signup(@RequestBody UserRequestDto userDto) {
        UserRequestDto user = UserRequestDto.builder()
                .nickname(userDto.getNickname())
                .build();
        try {
            userService.join(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 여기서 토큰 관련 처리
        return ResponseEntity.ok(UserResponseDto.builder().nickname(userDto.getNickname()).build());
    }

}
