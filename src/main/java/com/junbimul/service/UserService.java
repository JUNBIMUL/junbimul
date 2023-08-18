package com.junbimul.service;

import com.junbimul.dto.request.UserLoginRequestDto;
import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserLoginResponseDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.dto.response.UserSignupResponseDto;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    public UserSignupResponseDto join(UserSignupRequestDto userSignupRequestDto);
    public List<UserResponseDto> getUserList();
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) throws NoSuchAlgorithmException;
    public boolean checkUserIdDuplicated(String userId);
    public boolean checkUserNicknameDuplicated(String nickname);
}
