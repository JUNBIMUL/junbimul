package com.junbimul.service;

import com.junbimul.common.ConstProperties;
import com.junbimul.common.SHA256;
import com.junbimul.config.JwtUtil;
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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@EnableConfigurationProperties(ConstProperties.class)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConstProperties constProperties;
    private final JwtUtil jwtUtil;

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
        if (findUserList.size() == 0) {
            throw new UserApiException(UserErrorCode.USER_USERID_NOT_FOUND);
        }
        return findUserList.get(0);
    }

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) throws NoSuchAlgorithmException {
        List<User> findUserList = userRepository.findByLoginId(userLoginRequestDto.getLoginId());
        checkUserIdExists(findUserList);
        User findUser = findUserList.get(0);
        checkUserPassword(userLoginRequestDto, findUser);
        String accessToken = jwtUtil.generateAccessToken(findUser.getLoginId());
        String refreshToken = jwtUtil.generateRefreshToken(findUser.getLoginId());
        findUser.settingToken(accessToken, refreshToken);
        return UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Override
    public boolean checkUserIdDuplicated(String loginId) {
        checkLoginidLength(loginId);
        return userRepository.findByLoginId(loginId).size() == 1;
    }


    @Override
    public boolean checkUserNicknameDuplicated(String nickname) {
        checkNicknameLength(nickname);
        return userRepository.findByNickname(nickname).size() == 1;
    }

    @Override
    public String getAccessToken(Long id) {
        User findUser = userRepository.findById(id);
        return findUser.getAccessToken();
    }

    @Override
    public String getRefreshToken(Long id) {
        User findUser = userRepository.findById(id);
        return findUser.getRefreshToken();
    }

    public void checkLoginidLength(String userId) {
        if (userId.length() == 0) {
            throw new UserApiException(UserErrorCode.USER_USERID_LENGTH_ZERO);
        }
        if (userId.length() > constProperties.getUserLoginidLength()) {
            throw new UserApiException(UserErrorCode.USER_USERID_LENGTH_OVER);
        }
    }

    public void checkNicknameLength(String nickname) {
        if (nickname.length() == 0) {
            throw new UserApiException(UserErrorCode.USER_USERID_LENGTH_ZERO);
        }
        if (nickname.length() > constProperties.getUserNicknameLength()) {
            throw new UserApiException(UserErrorCode.USER_USERID_LENGTH_OVER);
        }
    }

    public void checkUserPassword(UserLoginRequestDto userLoginRequestDto, User findUser) throws NoSuchAlgorithmException {
        if (!findUser.getPassword().equals(new SHA256().encrypt(userLoginRequestDto.getPassword()))) {
            throw new UserApiException(UserErrorCode.USER_PASSWORD_INCORRECT);
        }
    }

    public void checkUserIdExists(List<User> findUserList) {
        if (findUserList.size() != 1) {
            throw new UserApiException(UserErrorCode.USER_USERID_NOT_FOUND);
        }
    }


}