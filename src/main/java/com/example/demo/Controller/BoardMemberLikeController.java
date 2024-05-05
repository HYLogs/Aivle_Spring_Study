package com.example.demo.Controller;

import com.example.demo.Dto.BoardMemberLikeDto;
import com.example.demo.Service.BoardMemberLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/test/like", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardMemberLikeController {

    private final BoardMemberLikeService boardMemberLikeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/active")
    public boolean active(@RequestBody BoardMemberLikeDto boardMemberLikeDto){
        boolean likeStatus = boardMemberLikeService.setLike(boardMemberLikeDto.getBoardId(), boardMemberLikeDto.getMemberEmail());

        return likeStatus;
    }

    @GetMapping("/readBoardLikeCount/{boardId}")
    public ResponseEntity<Integer> readBoardLikeCount(@PathVariable("boardId") Long boardId){
        List<BoardMemberLikeDto> boardMemberLikes = boardMemberLikeService.readBoardLike(boardId);

        if (!boardMemberLikes.isEmpty()){
            return ResponseEntity.ok(boardMemberLikes.size());
        }
        else{
            return ResponseEntity.badRequest().body(0);
        }
    }

    // 해당 케시판에 좋아요 누른 사람들 보기
    @GetMapping("/readBoardLike/{boardId}")
    public ResponseEntity<List<BoardMemberLikeDto>> readBoardLike(@PathVariable("boardId") Long boardId){
        List<BoardMemberLikeDto> boardMemberLikes = boardMemberLikeService.readBoardLike(boardId);

        if (!boardMemberLikes.isEmpty()){
            return ResponseEntity.ok(boardMemberLikes);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/readMemberLikeCount/{userEmail}")
    public ResponseEntity<Integer> readMemberLikeCount(@PathVariable("userEmail") String userEmail){
        List<BoardMemberLikeDto> boardMemberLikes = boardMemberLikeService.readUserLike(userEmail);

        if (!boardMemberLikes.isEmpty()){
            return ResponseEntity.ok(boardMemberLikes.size());
        }
        else{
            return ResponseEntity.badRequest().body(0);
        }
    }

    // 내가 좋아요 누른 게시판 보기
    @GetMapping("/readMemberLike/{userEmail}")
    public ResponseEntity<List<BoardMemberLikeDto>> readMemberLike(@PathVariable("userEmail") String userEmail){
        List<BoardMemberLikeDto> boardMemberLikes = boardMemberLikeService.readUserLike(userEmail);

        if (!boardMemberLikes.isEmpty()){
            return ResponseEntity.ok(boardMemberLikes);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }
}
