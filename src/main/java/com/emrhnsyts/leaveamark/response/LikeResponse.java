package com.emrhnsyts.leaveamark.response;

import com.emrhnsyts.leaveamark.entity.Like;
import lombok.Data;

@Data
public class LikeResponse {
    private Long id;
    private Long userId;
    private Long commentId;

    public LikeResponse(Like like) {
        this.id = like.getId();
        this.userId = like.getUser().getId();
        this.commentId = like.getComment().getId();
    }
}
