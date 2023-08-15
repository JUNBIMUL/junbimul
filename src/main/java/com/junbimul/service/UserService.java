package com.junbimul.service;

import com.junbimul.domain.User;
import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.dto.response.UserSignupResponseDto;
import com.junbimul.exception.ErrorCode;
import com.junbimul.exception.user.UserNicknameDuplicateException;
import com.junbimul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserSignupResponseDto join(UserSignupRequestDto userSignupRequestDto) {
        String nickname = userSignupRequestDto.getNickname();
        List<User> findUserList = userRepository.findByNickname(nickname);
        System.out.println("findUserList = " + findUserList.size());
        if (findUserList.size() != 0) {
            throw new UserNicknameDuplicateException(ErrorCode.USER_DUPLICATE_EXCEPTION);
        }
        User signupUser = User.builder().nickname(nickname).build();
        userRepository.save(signupUser);
        Long madeId = userRepository.findById(signupUser.getId()).getId();

        return UserSignupResponseDto.builder()
                .userId(madeId)
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
}