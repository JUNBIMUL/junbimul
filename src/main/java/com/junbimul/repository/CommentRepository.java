package com.junbimul.repository;

import com.junbimul.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    public List<Comment> findCommentsByBoard(Long boardId) {
        return em.createQuery(
                        "SELECT c FROM Comment c " +
                                "JOIN FETCH c.board b " +
                                "JOIN FETCH c.user comment_user " +
                                "JOIN FETCH b.user board_user " +
                                "WHERE b.id = :boardId",
                        Comment.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public Comment findById(Long commentId) {
        return em.find(Comment.class, commentId);
    }
}
