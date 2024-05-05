package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "member")
//@Table(indexes = {@Index(name = "member_index", columnList = "member_id")}) // 인덱싱
@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<BoardMemberLike> boardMemberLikes = new ArrayList<>();

    //==연관관계 메서드==//
    public void addBoardMemberLike(BoardMemberLike boardMemberLike) {
        this.boardMemberLikes.add(boardMemberLike);
        boardMemberLike.setMember(this);
    }
}
