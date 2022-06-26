package com.emrhnsyts.leaveamark.repository;

import com.emrhnsyts.leaveamark.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUser_Id(Long userId);
}
