package com.example.demo.Controller;

import com.example.demo.Dto.BoardDto;
import com.example.demo.Entity.Board;
import com.example.demo.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/test/board", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

    private final BoardService boardService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<BoardDto> create(@RequestBody Board board){
        BoardDto createdBoard = boardService.create(board);

        if (createdBoard != null){
            return ResponseEntity.ok(createdBoard);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<BoardDto>> readAll(){
        List<BoardDto> boardDtos = boardService.readAll();

        if (!boardDtos.isEmpty()){
            return ResponseEntity.ok(boardDtos);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 페이지 별로 불러오기 ex) localhost:8888/test/board/read?page=0&size=3
    @GetMapping("/read")
    public ResponseEntity<List<BoardDto>> readPage(@PageableDefault(size = 2) Pageable pageable){
        List<BoardDto> boardDtos = boardService.readAllPageable(pageable);

        if (!boardDtos.isEmpty()){
            return ResponseEntity.ok(boardDtos);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 내 게시물만 불러오기
    @GetMapping("/readMyBoard")
    public ResponseEntity<List<BoardDto>> readMyBoard(){
        String userName = "HY";
        List<BoardDto> boardDtos = boardService.readMyBoard(userName);

        if (!boardDtos.isEmpty()){
            return ResponseEntity.ok(boardDtos);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 해당 게시판이 내 것인지 확인
    @GetMapping("/check/{boardId}")
    public ResponseEntity<Boolean> checkMyBoard(@PathVariable Long boardId){
        String userName = "HY";
        boolean isMine = boardService.checkMyBoard(boardId, userName);

        if (isMine){
            return ResponseEntity.ok(isMine);
        }
        else{
            return ResponseEntity.badRequest().body(isMine);
        }
    }

    @PutMapping("/update/{boardId}")
    public ResponseEntity<BoardDto> update(@PathVariable Long boardId, @RequestBody Board board){
        BoardDto updatedBoards = boardService.update(boardId, board.getContent());

        if (updatedBoards != null){
            return ResponseEntity.ok(updatedBoards);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<BoardDto> delete(@PathVariable Long boardId){
        BoardDto deletedBoards = boardService.delete(boardId);

        if (deletedBoards != null){
            return ResponseEntity.ok(deletedBoards);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<BoardDto>> boardSearch(@PathVariable String keyword){
        List<BoardDto> boardDtos = boardService.getBoardKeyword(keyword);

        if (!boardDtos.isEmpty()){
            return ResponseEntity.ok(boardDtos);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }
}
