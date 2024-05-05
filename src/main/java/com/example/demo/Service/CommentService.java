package com.example.demo.Service;

import com.example.demo.Dto.BoardDto;
import com.example.demo.Dto.CommentDto;
import com.example.demo.Entity.Board;
import com.example.demo.Entity.Comment;
import com.example.demo.Repository.BoardRepository;
import com.example.demo.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public List<CommentDto> convertToDto(List<Comment> boards) {
        return boards.stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long boardId, CommentDto commentDto){
        Optional<Board> boards = boardRepository.findById(boardId);
        if (boards.isEmpty()){
            return null;
        }
        Board board = boards.get();

        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setCommentWriter("HY");

        Comment createdComment = commentRepository.save(comment);
        CommentDto createdCommentDto = new CommentDto(createdComment);

        return createdCommentDto;
    }

    public List<CommentDto> readAll(Long boardId){
        Optional<Board> boards = boardRepository.findById(boardId);
        if (boards.isEmpty()){
            return null;
        }
        Board board = boards.get();
        BoardDto boardDto = new BoardDto(board);

        return boardDto.getComments();
    }

    public List<CommentDto> readMyComment(String commentWriter){
        List<Comment> comments = commentRepository.findByCommentWriter(commentWriter);
        List<CommentDto> commentDtos = convertToDto(comments);
        return commentDtos;
    }

    public boolean checkMyComment(Long id, String commentWriter){
        Optional<Comment> comments = commentRepository.findById(id);
        if (comments.isEmpty()){
            return false;
        }

        Comment comment = comments.get();

        boolean result = false;
        if (comment.getCommentWriter().equals(commentWriter)){
            result = true;
        }

        return result;
    }

    @Transactional
    public CommentDto update(Long id, String content){
        Optional<Comment> comments = commentRepository.findById(id);
        if (comments.isEmpty()){
            return null;
        }

        Comment comment = comments.get();
        comment.setCommentContent(content);

        CommentDto commentDto = new CommentDto(comment);
        return commentDto;
    }

    @Transactional
    public CommentDto delete(Long id){
        Optional<Comment> comments = commentRepository.findById(id);
        if (comments.isEmpty()){
            return null;
        }

        Comment comment = comments.get();
        commentRepository.delete(comment);

        CommentDto commentDto = new CommentDto(comment);
        return commentDto;
    }
}
