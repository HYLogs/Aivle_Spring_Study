package com.example.demo.Controller;

import com.example.demo.Dto.CommentDto;
import com.example.demo.Entity.Comment;
import com.example.demo.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/test/board/{boardId}/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<CommentDto> create(@PathVariable("boardId") Long boardId, @RequestBody CommentDto comment){
        CommentDto createdBoard = commentService.create(boardId, comment);

        if (createdBoard != null){
            return ResponseEntity.ok(createdBoard);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/read")
    public ResponseEntity<List<CommentDto>> readAll(@PathVariable("boardId") Long boardId){
        List<CommentDto> comments = commentService.readAll(boardId);

        if (!comments.isEmpty()){
            return ResponseEntity.ok(comments);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 내 댓글만 불러오기
    @GetMapping("/readMyComment")
    public ResponseEntity<List<CommentDto>> readMyComment(){
        String userName = "HY";
        List<CommentDto> comments = commentService.readMyComment(userName);

        if (!comments.isEmpty()){
            return ResponseEntity.ok(comments);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 해당 댓글이 내 것인지 확인
    @GetMapping("/check/{commentId}")
    public ResponseEntity<Boolean> checkMyComment(@PathVariable("commentId") Long commentId){
        String userName = "HY";
        boolean isMine = commentService.checkMyComment(commentId, userName);

        if (isMine){
            return ResponseEntity.ok(isMine);
        }
        else{
            return ResponseEntity.badRequest().body(isMine);
        }
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentDto> update(@PathVariable("commentId") Long commentId, @RequestBody Comment comment){
        CommentDto updatedComment = commentService.update(commentId, comment.getCommentContent());

        if (updatedComment != null){
            return ResponseEntity.ok(updatedComment);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<CommentDto> delete(@PathVariable("commentId") Long commentId){
        CommentDto deletedComment = commentService.delete(commentId);

        if (deletedComment != null){
            return ResponseEntity.ok(deletedComment);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }
}
