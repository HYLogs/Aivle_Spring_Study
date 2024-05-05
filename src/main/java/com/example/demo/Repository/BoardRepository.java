package com.example.demo.Repository;

import com.example.demo.Entity.Board;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
//    @Cacheable("boardsByTitleContaining")
//    List<Board> findByTitleContaining(String keyword);

    List<Board> findCustomByTitleContaining(String title);
    List<Board> findByWriter(String writer);
    Page<Board> findAll(Pageable pageable);
}
