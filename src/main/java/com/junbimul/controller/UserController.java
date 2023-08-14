package com.junbimul.controller;

import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserSignupResponseDto;
import com.junbimul.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "유저")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    @Operation(summary = "회원 가입")
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody UserSignupRequestDto userDto) {
        return ResponseEntity.ok(userService.join(userDto));
    }

}
