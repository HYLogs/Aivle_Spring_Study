package com.example.demo.Service;

import com.example.demo.Entity.Board;
import com.example.demo.Entity.Comment;
import com.example.demo.Repository.BoardRepository;
import com.example.demo.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Comment create(Long boardId, Comment comment){
        Optional<Board> opBoard = boardRepository.findById(boardId);
        if (opBoard.isEmpty()){
            return null;
        }
        Board board = opBoard.get();

        comment.setBoard(board);
        comment.setCommentWriter("HY");

        Comment createdComment = commentRepository.save(comment);

        return createdComment;
    }

    public List<Comment> readAll(Long boardId){
        Optional<Board> opBoard = boardRepository.findById(boardId);
        if (opBoard.isEmpty()){
            return null;
        }
        Board board = opBoard.get();

        return board.getComments();
    }

    public List<Comment> readMyComment(String commentWriter){
        List<Comment> comments = commentRepository.findByCommentWriter(commentWriter);

        return comments;
    }

    public boolean checkMyComment(Long id, String commentWriter){
        Optional<Comment> opComment = commentRepository.findById(id);
        if (opComment.isEmpty()){
            return false;
        }

        Comment comment = opComment.get();

        boolean result = false;
        if (comment.getCommentWriter().equals(commentWriter)){
            result = true;
        }

        return result;
    }

    @Transactional
    public Comment update(Long id, String content){
        Optional<Comment> opComment = commentRepository.findById(id);
        if (opComment.isEmpty()){
            return null;
        }

        Comment comment = opComment.get();
        comment.setCommentContent(content);
        return comment;
    }

    @Transactional
    public Comment delete(Long id){
        Optional<Comment> opComment = commentRepository.findById(id);
        if (opComment.isEmpty()){
            return null;
        }

        Comment comment = opComment.get();
        commentRepository.delete(comment);

        return comment;
    }
}
