package com.junbimul.service;

import com.junbimul.common.SHA256;
import com.junbimul.domain.User;
import com.junbimul.dto.request.UserLoginRequestDto;
import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserLoginResponseDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.dto.response.UserSignupResponseDto;
import com.junbimul.error.ErrorCheckMethods;
import com.junbimul.error.exception.UserApiException;
import com.junbimul.error.model.UserErrorCode;
import com.junbimul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) throws NoSuchAlgorithmException {
        List<User> findUserList = userRepository.findByUserId(userLoginRequestDto.getUserId());
        checkUserIdExists(findUserList);
        User findUser = findUserList.get(0);
        checkUserPassword(userLoginRequestDto, findUser);
        return UserLoginResponseDto.builder()
                .userKey(findUser.getId())
                .build();
    }


    @Override
    public boolean checkUserIdDuplicated(String userId) {
        checkUserIdLength(userId);
        return userRepository.findByUserId(userId).size() == 1;
    }


    @Override
    public boolean checkUserNicknameDuplicated(String nickname) {
        checkNicknameLength(nickname);
        return userRepository.findByNickname(nickname).size() == 1;
    }


}