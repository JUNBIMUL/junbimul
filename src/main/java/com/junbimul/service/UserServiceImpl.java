package com.junbimul.service;

import com.junbimul.common.CookieUtil;
import com.junbimul.config.security.JwtTokenUtil;
import com.junbimul.domain.User;
import com.junbimul.dto.request.UserLoginRequestDto;
import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserLoginResponseDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.dto.response.UserSignupResponseDto;
import com.junbimul.error.exception.UserApiException;
import com.junbimul.error.model.UserErrorCode;
import com.junbimul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import static com.junbimul.error.ErrorCheckMethods.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserSignupResponseDto join(UserSignupRequestDto userSignupRequestDto) {
        String nickname = userSignupRequestDto.getNickname();
        List<User> findUserList = userRepository.findByNickname(nickname);
        if (findUserList.size() != 0) {
            throw new UserApiException(UserErrorCode.USER_DUPLICATE_EXCEPTION);
        }
        if (userSignupRequestDto.getNickname().length() > 30) {
            throw new UserApiException(UserErrorCode.USER_NICKNAME_LENGTH_OVER);
        }
        if (userSignupRequestDto.getNickname().length() == 0) {
            throw new UserApiException(UserErrorCode.USER_NICKNAME_LENGTH_ZERO);
        }

        User signupUser = User.builder()
                .nickname(nickname)
                .build();
        Long madeUserId = userRepository.save(signupUser);

        return UserSignupResponseDto.builder()
                .userId(madeUserId)
                .build();
    }

    public List<UserResponseDto> getUserList() {
        List<User> findUsers = userRepository.findUsers();
        return findUsers
                .stream()
                .map(u -> UserResponseDto.builder()
                        .userId(u.getId())
                        .nickname(u.getNickname())
                        .createdAt(u.getCreatedAt())
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public User getUserByLoginId(String loginId) {
        List<User> findUserList = userRepository.findByLoginId(loginId);
        if (findUserList.size() != 0) {
            throw new UserApiException(UserErrorCode.USER_USERID_NOT_FOUND);
        }
        return findUserList.get(0);
    }

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) throws NoSuchAlgorithmException {
        List<User> findUserList = userRepository.findByLoginId(userLoginRequestDto.getLoginId());
        checkUserIdExists(findUserList);
        User findUser = findUserList.get(0);
        checkUserPassword(userLoginRequestDto, findUser);
        String accessToken = JwtTokenUtil.createAccessToken(findUser.getLoginId(), "kk", 1000 * 30);// 30초
        String refreshToken = JwtTokenUtil.createRefreshToken(findUser.getLoginId(), "kk", 1000 * 60 * 60 * 24);// 1일
        CookieUtil.setAccessToken(response, accessToken);
        CookieUtil.setRefreshToken(response, refreshToken);
        findUser.settingToken(accessToken, refreshToken);

        return UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Override
    public boolean checkUserIdDuplicated(String loginId) {
        checkUserIdLength(loginId);
        return userRepository.findByLoginId(loginId).size() == 1;
    }


    @Override
    public boolean checkUserNicknameDuplicated(String nickname) {
        checkNicknameLength(nickname);
        return userRepository.findByNickname(nickname).size() == 1;
    }


}