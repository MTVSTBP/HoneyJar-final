package com.tbp.honeyjar.post.dao;

import com.tbp.honeyjar.post.dto.PostLikeRequestDto;
import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.dto.PostRequestDTO;
import com.tbp.honeyjar.post.dto.PostResponseDTO;
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
}