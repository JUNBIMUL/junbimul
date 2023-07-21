package com.junbimul.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", length = 30)
    private String nickname;

    @Column(name = "created_at",
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // 필요한가?
//    @OneToMany(mappedBy = "user")
//    private List<Board> boards = new ArrayList<>();

    @Builder
    public User(String nickname) {
        this.nickname = nickname;
    }
}
