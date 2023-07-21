package com.junbimul.service;

import com.junbimul.domain.User;
import com.junbimul.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    @Test
    void 회원가입() throws Exception {
        //given
        User user1 = createUser("닉네임1");
        //when
        userRepository.save(user1);
        //then
        Long findId = em.find(User.class, user1.getId()).getId();
        assertThat(user1.getId()).isEqualTo(findId);

    }

    private User createUser(String userNickname) {
        User user = User.builder().nickname(userNickname).build();
        return user;
    }
}