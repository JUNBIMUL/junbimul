package com.junbimul.controller;

import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserSignupResponseDto;
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
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody UserSignupRequestDto userDto) {
        return ResponseEntity.ok(userService.join(userDto));
    }

}
