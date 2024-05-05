package com.example.demo.Dto;

import com.example.demo.Entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String commentWriter;
    private String commentContent;
    private Long boardId;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.commentWriter = comment.getCommentWriter();
        this.commentContent = comment.getCommentContent();
        this.boardId = comment.getBoard().getId();
    }
}
