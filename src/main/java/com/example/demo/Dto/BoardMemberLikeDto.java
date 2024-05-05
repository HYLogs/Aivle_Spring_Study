package com.example.demo.Dto;

import com.example.demo.Entity.BoardMemberLike;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardMemberLikeDto {
    private Long id;
    private Long boardId;
    private String memberEmail;

    public BoardMemberLikeDto(BoardMemberLike boardMemberLike) {
        this.id = boardMemberLike.getId();
        this.boardId = boardMemberLike.getBoard().getId();
        this.memberEmail = boardMemberLike.getMember().getEmail();
    }
}
