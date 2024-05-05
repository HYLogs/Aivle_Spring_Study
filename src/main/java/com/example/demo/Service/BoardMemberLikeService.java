package com.example.demo.Service;

import com.example.demo.Dto.BoardDto;
import com.example.demo.Dto.BoardMemberLikeDto;
import com.example.demo.Dto.MemberDto;
import com.example.demo.Entity.Board;
import com.example.demo.Entity.BoardMemberLike;
import com.example.demo.Entity.Member;
import com.example.demo.Repository.BoardMemberLikeRepository;
import com.example.demo.Repository.BoardRepository;
import com.example.demo.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardMemberLikeService {

    private final BoardMemberLikeRepository boardMemberLikeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public boolean setLike(long boardId, String userEmail){
        BoardMemberLike boardMemberLike = boardMemberLikeRepository.findByBoard_IdAndMember_Email(boardId, userEmail);

        boolean result;
        if (boardMemberLike != null){
            boardMemberLikeRepository.delete(boardMemberLike);
            result = false;
        }
        else{
            BoardMemberLike newBoardMemberLike = new BoardMemberLike();

            Member member = memberRepository.findByEmail(userEmail);
            Optional<Board> boards = boardRepository.findById(boardId);
            if (boards.isEmpty() || member == null){
                System.out.println("사용자나 게시판이 존재하지 않음.");
                return false;
            }

            member.addBoardMemberLike(newBoardMemberLike);
            boards.get().addBoardMemberLike(newBoardMemberLike);

            boardMemberLikeRepository.save(newBoardMemberLike);
            result = true;
        }

        return result;
    }

    public List<BoardMemberLikeDto> readBoardLike(long boardId){
        Optional<Board> boards = boardRepository.findById(boardId);
        if (boards.isEmpty()){
            System.out.println("게시판이 존재하지 않음.");
            return null;
        }

        Board board = boards.get();
        BoardDto boardDto = new BoardDto(board);

        return boardDto.getBoardMemberLikes();
    }

    public List<BoardMemberLikeDto> readUserLike(String userEmail){
        Member member = memberRepository.findByEmail(userEmail);

        if (member == null){
            System.out.println("멤버가 존재하지 않음.");
            return null;
        }

        MemberDto memberDto = new MemberDto(member);

        return memberDto.getBoardMemberLikes();
    }
}
