package com.tbp.honeyjar.post.dao;

import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.dto.PostRequestDTO;
import com.tbp.honeyjar.post.dto.PostResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper {

    List<PostListDTO> findAllPost(Map<String, Object> params);

    void createPost(PostRequestDTO postRequestDTO);

    List<PostListDTO> findAllPost();

    PostResponseDTO findPostById(Long postId);

    void updatePost(PostRequestDTO addPostRequestDTO);

    Long findPlaceIdByPostId(Long postId);

    void softDeletePost(Long postId);

    int commentCount(Long postId);
}