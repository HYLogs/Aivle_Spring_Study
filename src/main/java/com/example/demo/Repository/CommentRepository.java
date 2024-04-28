package com.example.demo.Repository;

import com.example.demo.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCommentWriter(String commentWriter);
}
