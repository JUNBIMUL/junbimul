package com.junbimul.repository;

import com.junbimul.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long save(User user) {
        em.persist(user);
        return em.find(User.class, user.getId()).getId();
    }

    public List<User> findByNickname(String nickname) {
        return em.createQuery("select u from User u where u.nickname = :nickname", User.class)
                .setParameter("nickname", nickname)
                .getResultList();

    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findByLoginId(String loginId) {
        return em.createQuery("select u from User u where u.loginId = :loginId", User.class)
                .setParameter("loginId", loginId)
                .getResultList();

    }

    public List<User> findUsers() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

//    public List<User> findByUserId(String userId) {
//        return em.createQuery("select u from User u where u.loginId = :loginId", User.class)
//                .setParameter("loginId", loginId)
//                .getResultList();
//    }

}