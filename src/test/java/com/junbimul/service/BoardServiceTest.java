package com.junbimul.service;

import com.junbimul.domain.Board;
import com.junbimul.domain.User;
import com.junbimul.dto.response.BoardDetailResponseDto;
import org.assertj.core.api.Assertions;
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
        Long id = boardService.registerBoard(board);
        //then
        BoardDetailResponseDto findBoard = boardService.getBoardDetailById(id);
        Assertions.assertThat(id).isEqualTo(findBoard.getBoardId());

    }


    @Test
    void 게시글_전체조회() throws Exception {
        //given
        Board board = createBoard("게시글 1", "유저 1");
        Board board2 = createBoard("게시글 2", "유저 2");
        Board board3 = createBoard("게시글 3", "유저 3");
        //when
        boardService.registerBoard(board);
        boardService.registerBoard(board2);
        boardService.registerBoard(board3);
        //then
        Assertions.assertThat(boardService.findBoards().size()).isEqualTo(3);
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