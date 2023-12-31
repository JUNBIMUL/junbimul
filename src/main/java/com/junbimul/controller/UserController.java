package com.junbimul.controller;

import com.junbimul.dto.request.UserLoginRequestDto;
import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserLoginResponseDto;
import com.junbimul.dto.response.UserReissueResponseDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "유저")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "회원 가입")
    public ResponseEntity signup(@RequestBody UserSignupRequestDto userDto) {
        return ResponseEntity.ok(userService.join(userDto));
    }

    @GetMapping
    @Operation(summary = "회원 목록 보기")
    public ResponseEntity<List<UserResponseDto>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) throws NoSuchAlgorithmException {

        return ResponseEntity.ok(userService.login(userLoginRequestDto, response));
    }

    @GetMapping("/check-duplicate/userid")
    @Operation(summary = "ID 중복체크")
    public ResponseEntity checkUserIdDuplicate(@RequestParam String userid) {
        if (!userService.checkUserIdDuplicated(userid)) {
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/check-duplicate/nickname")
    @Operation(summary = "닉네임 중복체크")
    public ResponseEntity checkNicknameDuplicate(@RequestParam String nickname) {
        if (!userService.checkUserNicknameDuplicated(nickname)) {
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/reissue")
    @Operation(summary = "refresh token 재발급")
    public ResponseEntity<UserReissueResponseDto> reIssue(HttpServletRequest request) {
        return ResponseEntity.ok(userService.reissueToken(request));
    }
}
