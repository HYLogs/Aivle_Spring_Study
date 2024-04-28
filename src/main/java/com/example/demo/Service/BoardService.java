package com.example.demo.Service;


import com.example.demo.Entity.Board;
import com.example.demo.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board create(Board board){
        board.setWriter("HY");
        Board createdBoard = boardRepository.save(board);

        return createdBoard;
    }

    public List<Board> readAll(){
        List<Board> boards =  boardRepository.findAll();

        return boards;
    }

    public List<Board> readAllPageable(Pageable pageable){
        List<Board> boards = boardRepository.findAll(pageable).getContent();

        return boards;
    }

    public List<Board> readMyBoard(String writer){
        List<Board> boards = boardRepository.findByWriter(writer);

        return boards;
    }

    public boolean checkMyBoard(Long id, String writer){
        Optional<Board> boards = boardRepository.findById(id);
        if (boards.isEmpty()){
            return false;
        }

        boolean result = false;
        if (boards.get().getWriter().equals(writer)){
            result = true;
        }
        return result;
    }

    @Transactional
    public Board update(Long id, String content){
        Optional<Board> board = boardRepository.findById(id);
        if (board.isEmpty()){
            return null;
        }

        Board result = board.get();
        result.setContent(content);
        return result;
    }

    @Transactional
    public Board delete(Long id){
        Optional<Board> opBoard = boardRepository.findById(id);
        if (opBoard.isEmpty()){
            return null;
        }

        Board board = opBoard.get();
        boardRepository.delete(board);

        return board;
    }

    public List<Board> getBoardKeyword(String keyword){
        List<Board> posts = boardRepository.findCustomByTitleContaining(keyword);

        return posts;
    }
}
