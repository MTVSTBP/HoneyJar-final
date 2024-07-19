package com.tbp.honeyjar.post.dao;

import com.tbp.honeyjar.post.dto.AddPostRequestDTO;
import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.dto.PostRequestDTO;
import com.tbp.honeyjar.post.dto.PostResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    void createPost(PostRequestDTO postRequestDTO);

    List<PostListDTO> findAllPost();

    PostResponseDTO findPostById(Long postId);

    void updatePost(AddPostRequestDTO addPostRequestDTO);

}