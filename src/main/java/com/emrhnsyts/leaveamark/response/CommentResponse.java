package com.emrhnsyts.leaveamark.response;

import com.emrhnsyts.leaveamark.entity.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentResponse {
    private Long id;
    private String text;
    private Date createdAt;
    private Long userId;
    private List<LikeResponseWithUserId> likes;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();
        this.userId = comment.getUser().getId();
        this.likes = comment.getLikes().stream().map(like -> {
            return new LikeResponseWithUserId(like);
        }).collect(Collectors.toList());
    }
}
