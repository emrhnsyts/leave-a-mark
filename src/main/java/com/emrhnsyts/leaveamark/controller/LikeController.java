package com.emrhnsyts.leaveamark.controller;

import com.emrhnsyts.leaveamark.entity.Like;
import com.emrhnsyts.leaveamark.request.LikeRequest;
import com.emrhnsyts.leaveamark.response.LikeResponse;
import com.emrhnsyts.leaveamark.response.LikeResponseWithUserId;
import com.emrhnsyts.leaveamark.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public LikeResponse addLike(@RequestBody @Valid LikeRequest likeRequest) {
        return likeService.addLike(likeRequest);
    }

    @GetMapping("/{commentId}")
    public List<LikeResponseWithUserId> getLikesByCommentId(@PathVariable Long commentId) {
        return likeService.getLikesByCommentId(commentId);
    }

    @GetMapping
    public List<LikeResponse> getLikes() {
        return likeService.getLikes();
    }

    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable("likeId") Long likeId) {
        likeService.deleteLike(likeId);
    }

    @GetMapping("/{likeId}")
    public LikeResponse getLike(@PathVariable("likeId") Long likeId) {
        return likeService.getLike(likeId);
    }
}
