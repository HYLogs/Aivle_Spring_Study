package com.example.demo.Service;


import com.example.demo.Dto.BoardDto;
import com.example.demo.Entity.Board;
import com.example.demo.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardDto> convertToDto(List<Board> boards) {
        return boards.stream()
                .map(BoardDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardDto create(Board board){
        board.setWriter("HY");
        Board createdBoard = boardRepository.save(board);
        BoardDto boardDto = new BoardDto(createdBoard);

        return boardDto;
    }

    public List<BoardDto> readAll(){
        List<Board> boards =  boardRepository.findAll();
        List<BoardDto> boardDtos = convertToDto(boards);
        return boardDtos;
    }

    public List<BoardDto> readAllPageable(Pageable pageable){
        List<Board> boards = boardRepository.findAll(pageable).getContent();
        List<BoardDto> boardDtos = convertToDto(boards);
        return boardDtos;
    }

    public List<BoardDto> readMyBoard(String writer){
        List<Board> boards = boardRepository.findByWriter(writer);
        List<BoardDto> boardDtos = convertToDto(boards);
        return boardDtos;
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
    public BoardDto update(Long id, String content){
        Optional<Board> boards = boardRepository.findById(id);
        if (boards.isEmpty()){
            return null;
        }

        Board board = boards.get();
        board.setContent(content);

        BoardDto boardDto = new BoardDto(board);
        return boardDto;
    }

    @Transactional
    public BoardDto delete(Long id){
        Optional<Board> boards = boardRepository.findById(id);
        if (boards.isEmpty()){
            return null;
        }

        Board board = boards.get();
        boardRepository.delete(board);

        BoardDto boardDto = new BoardDto(board);
        return boardDto;
    }

    public List<BoardDto> getBoardKeyword(String keyword){
        List<Board> boards = boardRepository.findCustomByTitleContaining(keyword);
        List<BoardDto> boardDtos = convertToDto(boards);
        return boardDtos;
    }
}
