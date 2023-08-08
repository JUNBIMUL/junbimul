package com.junbimul.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "content", length = 255)
    private String content;

    @Column(name = "created_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "view_cnt")
    @ColumnDefault("0")
    private Long viewCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void updateViewCount(){
        this.viewCnt = this.viewCnt + 1;
    }
    public void modify(String title, String content, LocalDateTime updatedAt) {
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
    }
    public void delete( LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;

    }
}