package com.example.demo.Dto;

import com.example.demo.Entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String memberName;
    private String memberPassword;
    private String memberEmail;

    // BoardMemberLikeDto의 리스트를 받아서 순환참조 방지
    private List<BoardMemberLikeDto> boardMemberLikes;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.memberName = member.getName();
        this.memberPassword = member.getPassword();
        this.memberEmail = member.getEmail();
        this.boardMemberLikes = member.getBoardMemberLikes().stream().map(BoardMemberLikeDto::new).collect(Collectors.toList());
    }
}
