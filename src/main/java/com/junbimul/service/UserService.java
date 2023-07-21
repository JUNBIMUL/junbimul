package com.junbimul.service;

import com.junbimul.domain.User;
import com.junbimul.dto.request.UserRequestDto;
import com.junbimul.dto.response.UserResponseDto;
import com.junbimul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public void join(UserRequestDto user) {
        if (userRepository.findByName(user.getNickname()).size() == 0) {
            userRepository.save(
                    User.builder().nickname(user.getNickname())
                            .build());
        } else {
            throw new IllegalStateException("이미 ID 존재함");
        }
    }

    public User getUser(Long id) {
        return userRepository.findById(id);
    }


}