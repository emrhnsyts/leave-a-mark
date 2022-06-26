package com.emrhnsyts.leaveamark.repository;

import com.emrhnsyts.leaveamark.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByComment_Id(Long commentId);

    List<Like> findByUser_Id(Long userId);
}
