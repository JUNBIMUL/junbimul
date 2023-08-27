package com.junbimul.service;

import com.junbimul.domain.User;
import com.junbimul.dto.request.UserLoginRequestDto;
import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserLoginResponseDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.dto.response.UserSignupResponseDto;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    public List<UserResponseDto> getUserList();

    public UserSignupResponseDto join(UserSignupRequestDto userSignupRequestDto);

    public User getUserByLoginId(String loginId);

    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) throws NoSuchAlgorithmException;

    public boolean checkUserIdDuplicated(String userId);

    public boolean checkUserNicknameDuplicated(String nickname);

    public String getAccessToken(Long id);

    public String getRefreshToken(Long id);
}
