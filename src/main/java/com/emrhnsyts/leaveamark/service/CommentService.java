package com.emrhnsyts.leaveamark.service;

import com.emrhnsyts.leaveamark.entity.Comment;
import com.emrhnsyts.leaveamark.exception.CommentNotFoundException;
import com.emrhnsyts.leaveamark.exception.LikeNotFoundException;
import com.emrhnsyts.leaveamark.repository.AppUserRepository;
import com.emrhnsyts.leaveamark.repository.CommentRepository;
import com.emrhnsyts.leaveamark.request.CommentChangeRequest;
import com.emrhnsyts.leaveamark.request.CommentRequest;
import com.emrhnsyts.leaveamark.response.CommentCreateResponse;
import com.emrhnsyts.leaveamark.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final AppUserRepository appUserRepository;

    public List<CommentResponse> getComments() {
        return commentRepository.findAll().stream().map(comment ->
        {
            return new CommentResponse(comment);
        }).collect(Collectors.toList());
    }

    public CommentResponse getComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (!optionalComment.isPresent()) {
            throw new CommentNotFoundException(String.format("Comment with %s id number not found.", commentId));
        }
        return new CommentResponse(optionalComment.get());
    }

    public CommentResponse updateComment(CommentChangeRequest commentChangeRequest) {
        Optional<Comment> optionalComment = commentRepository.findById(commentChangeRequest.getCommentId());
        if (!optionalComment.isPresent()) {
            throw new CommentNotFoundException(String.format("Comment with %s id number not found.", commentChangeRequest.getCommentId()));
        }
        Comment commentToBeChanged = optionalComment.get();
        commentToBeChanged.setText(commentChangeRequest.getText());
        commentRepository.save(commentToBeChanged);
        return new CommentResponse(commentToBeChanged);
    }

    public CommentCreateResponse addComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        comment.setUser(appUserRepository.findById(commentRequest.getUserId()).get());
        comment.setCreatedAt(new Date());
        commentRepository.save(comment);
        return new CommentCreateResponse(comment);
    }

    public void deleteComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (!optionalComment.isPresent()) {
            throw new CommentNotFoundException(String.format("Comment with %s id number not found.", commentId));
        }
        commentRepository.delete(optionalComment.get());
    }

    protected List<Comment> getCommentsForService() {
        return commentRepository.findAll();
    }

    protected Comment getCommentForService(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (!optionalComment.isPresent()) {
            throw new CommentNotFoundException(String.format("Comment with %s id number not found.", commentId));
        }
        return optionalComment.get();
    }


}

