package com.emrhnsyts.leaveamark.service;

import com.emrhnsyts.leaveamark.entity.AppUser;
import com.emrhnsyts.leaveamark.entity.Comment;
import com.emrhnsyts.leaveamark.entity.Like;
import com.emrhnsyts.leaveamark.exception.LikeNotFoundException;
import com.emrhnsyts.leaveamark.repository.LikeRepository;
import com.emrhnsyts.leaveamark.request.LikeRequest;
import com.emrhnsyts.leaveamark.response.LikeResponse;
import com.emrhnsyts.leaveamark.response.LikeResponseWithCommentId;
import com.emrhnsyts.leaveamark.response.LikeResponseWithUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final AppUserService appUserService;
    private final CommentService commentService;

    public List<LikeResponse> getLikes() {
        return likeRepository.findAll().stream().map(like -> {
            return new LikeResponse(like);
        }).collect(Collectors.toList());
    }

    public LikeResponse getLike(Long likeId) {
        Optional<Like> optionalLike = likeRepository.findById(likeId);
        if (!optionalLike.isPresent()) {
            throw new LikeNotFoundException(String.format("Like with %s id number not found.", likeId));
        }
        return new LikeResponse(optionalLike.get());
    }

    public List<LikeResponseWithUserId> getLikesByCommentId(Long commentId) {

        return likeRepository.findByComment_Id(commentId).stream().map(like -> {
            return new LikeResponseWithUserId(like);
        }).collect(Collectors.toList());
    }

    public List<LikeResponseWithCommentId> getLikesByUserId(Long userId) {
        return likeRepository.findByUser_Id(userId).stream().map(like -> {
            return new LikeResponseWithCommentId(like);
        }).collect(Collectors.toList());
    }

    public LikeResponse addLike(LikeRequest likeRequest) {
        AppUser appUser = appUserService.getUserForService(likeRequest.getUserId());
        Comment comment = commentService.getCommentForService(likeRequest.getCommentId());
        return new LikeResponse(likeRepository.save(new Like(appUser, comment)));
    }

    public void deleteLike(Long likeId) {
        Optional<Like> optionalLike = likeRepository.findById(likeId);
        if (!optionalLike.isPresent()) {
            throw new LikeNotFoundException(String.format("Like with %s id number not found.", likeId));
        }
        likeRepository.delete(optionalLike.get());
    }
}
