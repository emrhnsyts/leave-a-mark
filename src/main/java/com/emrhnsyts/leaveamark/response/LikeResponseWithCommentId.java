package com.emrhnsyts.leaveamark.response;

import com.emrhnsyts.leaveamark.entity.Like;
import lombok.Data;

@Data
public class LikeResponseWithCommentId {
    private Long id;
    private Long commentId;

    public LikeResponseWithCommentId(Like like) {
        this.id = like.getId();
        this.commentId = like.getComment().getId();
    }
}
