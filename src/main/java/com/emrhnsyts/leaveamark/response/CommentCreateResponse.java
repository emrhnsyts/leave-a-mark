package com.emrhnsyts.leaveamark.response;

import com.emrhnsyts.leaveamark.entity.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentCreateResponse {
    private Long id;
    private String text;
    private Date createdAt;
    private Long userId;


    public CommentCreateResponse(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();
        this.userId = comment.getUser().getId();
    }
}
