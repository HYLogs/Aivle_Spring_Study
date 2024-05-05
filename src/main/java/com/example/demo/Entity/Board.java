package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Table(name = "board_title")
@Entity
@Getter
@Setter
public class Board extends Time {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardMemberLike> boardMemberLikes = new ArrayList<>();

    //==연관관계 메서드==//
    public void addBoardMemberLike(BoardMemberLike boardMemberLike) {
        this.boardMemberLikes.add(boardMemberLike);
        boardMemberLike.setBoard(this);
    }
}
