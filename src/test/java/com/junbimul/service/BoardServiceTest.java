package com.junbimul.service;

import com.junbimul.domain.Board;
import com.junbimul.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    EntityManager em;

    @Test
    void 게시글_등록() throws Exception {
        //given
        Board board = createBoard("게시글 1", "유저 1");
        //when

    }


    @Test
    void 게시글_전체조회() throws Exception {
        //given
        Board board = createBoard("게시글 1", "유저 1");
        Board board2 = createBoard("게시글 2", "유저 2");
        Board board3 = createBoard("게시글 3", "유저 3");
        //when
        //then
    }

    private Board createBoard(String title, String userNickname) {
        User user = createAndPersistUser(userNickname);
        Board board = Board.builder()
                .title(title)
                .content("돼!")
                .user(user)
                .build();
        return board;
    }

    private User createAndPersistUser(String userNickname) {
        User user = User.builder().nickname(userNickname).build();
        em.persist(user);
        return user;
    }
}