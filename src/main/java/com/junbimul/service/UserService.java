package com.junbimul.service;

import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.dto.response.UserSignupResponseDto;

import java.util.List;

public interface UserService {
    public UserSignupResponseDto join(UserSignupRequestDto userSignupRequestDto);
    public List<UserResponseDto> getUserList();
}
