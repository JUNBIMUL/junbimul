package com.junbimul.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    private Long id;
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

}
