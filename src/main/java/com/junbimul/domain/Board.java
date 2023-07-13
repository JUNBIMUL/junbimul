package com.junbimul.domain;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Board {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long viewCnt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
