package com.junbimul.controller;

import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.dto.response.UserSignupResponseDto;
import com.junbimul.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "유저")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/user")
    @Operation(summary = "회원 가입")
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody UserSignupRequestDto userDto) {
        return ResponseEntity.ok(userService.join(userDto));
    }
    @GetMapping("/user")
    @Operation(summary = "회원 목록 보기")
    public ResponseEntity<List<UserResponseDto>> getUserList(){
        return ResponseEntity.ok(userService.getUserList());
    }

}
