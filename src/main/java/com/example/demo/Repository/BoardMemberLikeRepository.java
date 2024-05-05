package com.example.demo.Repository;

import com.example.demo.Entity.BoardMemberLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardMemberLikeRepository extends JpaRepository<BoardMemberLike, Long> {
    BoardMemberLike findByBoard_IdAndMember_Email(Long board_id, String member_email);
}
