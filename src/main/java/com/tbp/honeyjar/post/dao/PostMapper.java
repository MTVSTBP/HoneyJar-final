package com.tbp.honeyjar.post.dao;

import com.tbp.honeyjar.post.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    void createPost(PostRequestDTO postRequestDTO);

    List<PostListDTO> findAllPost();

    PostResponseDTO findPostById(Long postId);

    void updatePost(AddPostRequestDTO addPostRequestDTO);

    void likePost(PostLikeRequestDto requestDto);

    void unlikePost(PostLikeRequestDto requestDto);

    int getLikeCountByPostId(Long postId);

    int getIsLikedByPostIdAndUserId(Long postId, Long userId);

    float getRating(Long postId);

    void rating(PostRatingRequestDto requestDto);

    int getIsRatedByPostIdAndUserId(Long postId, Long userId);

    void ratingAgain(PostRatingRequestDto requestDto);
}