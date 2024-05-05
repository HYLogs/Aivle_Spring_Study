package com.example.demo.Dto;

import com.example.demo.Entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private List<CommentDto> comments = new ArrayList<>();
    private List<BoardMemberLikeDto> boardMemberLikes = new ArrayList<>();

    public BoardDto(Board board) {
        this.id = board.getId();
        this.writer = board.getWriter();
        this.title = board.getTitle();
        this.content = board.getContent();

        this.comments = board.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
        this.boardMemberLikes = board.getBoardMemberLikes().stream().map(BoardMemberLikeDto::new).collect(Collectors.toList());
    }
}
