package com.emrhnsyts.leaveamark.request;

import lombok.Data;

@Data
public class LikeRequest {
    private Long userId;
    private Long commentId;
}
