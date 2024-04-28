package com.example.demo.Controller;

import com.example.demo.Entity.Board;
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
    public ResponseEntity<Comment> create(@PathVariable("boardId") Long boardId, @RequestBody Comment comment){
        Comment createdBoard = commentService.create(boardId, comment);

        if (createdBoard != null){
            return ResponseEntity.ok(createdBoard);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/read")
    public List<Comment> readAll(@PathVariable("boardId") Long boardId){
        List<Comment> comments = commentService.readAll(boardId);
        return comments;
    }

    // 내 댓글만 불러오기
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readMyBoard")
    public List<Comment> readMyComment(){
        String userName = "HY";
        List<Comment> comments = commentService.readMyComment(userName);

        return comments;
    }

    // 해당 댓글이 내 것인지 확인
    @ResponseStatus(HttpStatus.OK)
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
    public ResponseEntity<Comment> update(@PathVariable("commentId") Long commentId, @RequestBody Comment comment){
        Comment updatedComment = commentService.update(commentId, comment.getCommentContent());

        if (updatedComment != null){
            return ResponseEntity.ok(updatedComment);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Comment> delete(@PathVariable("commentId") Long commentId){
        Comment deletedComment = commentService.delete(commentId);

        if (deletedComment != null){
            return ResponseEntity.ok(deletedComment);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }
}
