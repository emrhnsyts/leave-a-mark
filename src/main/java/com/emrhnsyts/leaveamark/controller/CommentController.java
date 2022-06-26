package com.emrhnsyts.leaveamark.controller;

import com.emrhnsyts.leaveamark.request.CommentChangeRequest;
import com.emrhnsyts.leaveamark.request.CommentRequest;
import com.emrhnsyts.leaveamark.response.CommentCreateResponse;
import com.emrhnsyts.leaveamark.response.CommentResponse;
import com.emrhnsyts.leaveamark.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentResponse> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/{commentId}")
    public CommentResponse getComment(@PathVariable("commentId") Long commentId) {
        return commentService.getComment(commentId);
    }

    @PostMapping
    public CommentCreateResponse addComment(@RequestBody @Valid CommentRequest commentRequest) {
        return commentService.addComment(commentRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }

    @PutMapping
    public CommentResponse changeComment(@RequestBody @Valid CommentChangeRequest commentChangeRequest) {
        return commentService.updateComment(commentChangeRequest);
    }

}
