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

    public List findByName(String nickname) {
        return em.createQuery("select u from User u where u.nickname = :nickname")
                .setParameter("nickname", nickname)
                .getResultList();

    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

}
