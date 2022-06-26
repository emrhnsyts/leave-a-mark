package com.emrhnsyts.leaveamark.response;

import com.emrhnsyts.leaveamark.entity.Like;
import lombok.Data;

@Data
public class LikeResponseWithUserId {
    private Long id;
    private Long userId;

    public LikeResponseWithUserId(Like like) {
        this.id = like.getId();
        this.userId = like.getUser().getId();
    }
}
