package com.junbimul.service;

import com.junbimul.domain.User;
import com.junbimul.dto.request.UserLoginRequestDto;
import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserLoginResponseDto;
import com.junbimul.dto.response.UserReissueResponseDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.dto.response.UserSignupResponseDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    public List<UserResponseDto> getUserList();

    public UserSignupResponseDto join(UserSignupRequestDto userSignupRequestDto);

    public User getUserByLoginId(String loginId);

    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto, HttpServletResponse httpServletResponse) throws NoSuchAlgorithmException;

    public boolean checkUserIdDuplicated(String userId);

    public boolean checkUserNicknameDuplicated(String nickname);

    public String getAccessToken(Long id);

    public String getRefreshToken(Long id);

    public UserReissueResponseDto reissueToken(HttpServletRequest httpServletRequest);
}
