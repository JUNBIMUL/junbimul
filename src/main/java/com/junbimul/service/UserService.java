package com.junbimul.service;

import com.junbimul.domain.User;
import com.junbimul.dto.request.UserSignupRequestDto;
import com.junbimul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public Long join(UserSignupRequestDto userRequestDto) {
        if (userRepository.findByName(userRequestDto.getNickname()).size() == 0) {
            Long userId = userRepository.save(
                    User.builder()
                            .nickname(userRequestDto.getNickname())
                            .build());
        } else {
            throw new IllegalStateException("이미 ID 존재함");
        }
        return userRequestDto.getUserId();
    }

    public User getUser(Long id) {
        return userRepository.findById(id);
    }


}