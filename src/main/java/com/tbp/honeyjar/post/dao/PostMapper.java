package com.tbp.honeyjar.post.dao;

import com.tbp.honeyjar.post.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper {

    List<PostListDTO> findPostsByCategory(@Param("category") Long category,
                                          @Param("size") int size,
                                          @Param("offset") int offset);

    void createPost(PostRequestDTO postRequestDTO);

    List<PostListDTO> findAllPost();

    PostResponseDTO findPostById(Long postId);

    void updatePost(PostRequestDTO addPostRequestDTO);

    Long findPlaceIdByPostId(Long postId);

    void softDeletePost(Long postId);

    void likePost(PostLikeRequestDto requestDto);

    void unlikePost(PostLikeRequestDto requestDto);

    int getLikeCountByPostId(Long postId);

    int getIsLikedByPostIdAndUserId(Long postId, Long userId);

    float getRating(Long postId);

    void rating(PostRatingRequestDto requestDto);

    int getIsRatedByPostIdAndUserId(Long postId, Long userId);

    void ratingAgain(PostRatingRequestDto requestDto);
    int commentCount(Long postId);
}