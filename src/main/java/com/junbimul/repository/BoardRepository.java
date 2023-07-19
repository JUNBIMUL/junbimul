package com.junbimul.repository;

import com.junbimul.domain.Board;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    // 원하는 기능
    // 게시글 저장
    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    // 전체 가져오기 -> 추후 페이지네이션
    public List<Board> findAll(){
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    // 게시글 하나 가져오기
    public Board findOne(Long id) {
        return em.find(Board.class, id);
    }

    // 검색 기능

}
