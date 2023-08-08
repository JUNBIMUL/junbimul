package com.junbimul.service;

import com.junbimul.domain.User;
import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.dto.response.UserSignupResponseDto;
import com.junbimul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserSignupResponseDto join(UserSignupRequestDto userSignupRequestDto) {
        String nickname = userSignupRequestDto.getNickname();
        User signupUser = User.builder().nickname(nickname).build();
        userRepository.save(signupUser);

        return UserSignupResponseDto.builder()
                .userId(signupUser.getId())
                .build();
    }

    public User getUser(Long id) {
        return userRepository.findById(id);
    }


}