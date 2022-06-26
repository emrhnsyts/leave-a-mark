package com.emrhnsyts.leaveamark.response;

import com.emrhnsyts.leaveamark.entity.AppUser;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AppUserResponse {
    private Long userId;
    private String username;
    private String email;
    private List<LikeResponseWithCommentId> likes;
    private List<Long> commentIds;

    public AppUserResponse(AppUser appUser) {
        this.userId = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
        this.likes = appUser.getLikes().stream().map(like -> {
            return new LikeResponseWithCommentId(like);
        }).collect(Collectors.toList());
        this.commentIds = appUser.getComments().stream().map(comment -> {
            return comment.getId();
        }).collect(Collectors.toList());

    }
}
