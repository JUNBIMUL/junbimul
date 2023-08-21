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

    @Column(name = "login_id", length = 50)
    private String loginId;

    @Column(name = "nickname", length = 30)
    private String nickname;

    @Column(name = "created_at",
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "password",
            length = 255,
            nullable = false)
    private String password;

    private String accessToken;
    private String refreshToken;

    @Builder
    public User(String nickname) {
        this.nickname = nickname;
    }

    public void settingToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
