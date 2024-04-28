package com.example.demo.Controller;

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
    public ResponseEntity<Board> create(@RequestBody Board board){
        Board createdBoard = boardService.create(board);

        if (createdBoard != null){
            return ResponseEntity.ok(createdBoard);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readAll")
    public List<Board> readAll(){
        List<Board> boards = boardService.readAll();
        return boards;
    }

    // 페이지 별로 불러오기 ex) localhost:8888/test/board/read?page=0
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/read")
    public List<Board> readPage(@PageableDefault(size = 2) Pageable pageable){
        List<Board> boards = boardService.readAllPageable(pageable);

        return boards;
    }

    // 내 게시물만 불러오기
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readMyBoard")
    public List<Board> readMyBoard(){
        String userName = "HY";
        List<Board> boards = boardService.readMyBoard(userName);

        return boards;
    }

    // 해당 게시판이 내 것인지 확인
    @ResponseStatus(HttpStatus.OK)
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
    public ResponseEntity<Board> update(@PathVariable Long boardId, @RequestBody Board board){
        Board updatedBoards = boardService.update(boardId, board.getContent());

        if (updatedBoards != null){
            return ResponseEntity.ok(updatedBoards);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<Board> delete(@PathVariable Long boardId){
        Board deletedBoards = boardService.delete(boardId);

        if (deletedBoards != null){
            return ResponseEntity.ok(deletedBoards);
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/search/{keyword}")
    public List<Board> boardSearch(@PathVariable String keyword){
        List<Board> posts = boardService.getBoardKeyword(keyword);

        return posts;
    }
}
