package com.junbimul;

import com.junbimul.common.SHA256;
import com.junbimul.domain.Board;
import com.junbimul.domain.Comment;
import com.junbimul.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    private void init() throws NoSuchAlgorithmException {
        initService.DBInit();
    }


    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        private final EntityManager em;


        // user 1, user 2
        // 게시글 1, 2
        // 댓글 1, 2
        public void DBInit() throws NoSuchAlgorithmException {
            User user1 = User.builder()
                    .loginId("곤할영")
                    .password(new SHA256().encrypt("곤할영"))
                    .nickname("나롱치")
                    .build();
            User user2 = User.builder()
                    .loginId("침민구")
                    .password(new SHA256().encrypt("침민구"))
                    .nickname("침키민구")
                    .build();


            em.persist(user1);
            em.persist(user2);

            Board board1 = Board.builder()
                    .title("피고나령치")
                    .content("저는 천재 피보나치 + 나롱치입니다")
                    .user(user1).build();
            Board board2 = Board.builder()
                    .title("미군세균")
                    .content("피고나롱치 멋져요 끼에에에엑")
                    .user(user2).build();
            em.persist(board1);
            em.persist(board2);


            Comment comment1_1 = Comment.builder()
                    .board(board1)
                    .user(user1)
                    .content("악응능꺍뀰껡")
                    .build();
            Comment comment1_2 = Comment.builder()
                    .board(board1)
                    .user(user2)
                    .content("팬이에요!! 휘유 휘우(바람소리만 난다)")
                    .build();

            Comment comment2_1 = Comment.builder()
                    .board(board2)
                    .user(user1)
                    .content("좋아? 좋아?")
                    .build();

            Comment comment2_2 = Comment.builder()
                    .board(board2)
                    .user(user2)
                    .content("좋아! 좋아!")
                    .build();

            em.persist(comment1_1);
            em.persist(comment1_2);
            em.persist(comment2_1);
            em.persist(comment2_2);


        }

    }
}
