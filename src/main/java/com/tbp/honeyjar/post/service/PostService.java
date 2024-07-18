package com.tbp.honeyjar.post.service;



import com.tbp.honeyjar.post.dao.PostMapper;

import com.tbp.honeyjar.post.dto.AddPostRequestDTO;
import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.dto.PostRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostMapper postMapper;

    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public List<PostListDTO> findAllPost() {
        return postMapper.findAllPost();
    }

//    public PostResponseDTO findPostById(Long postId) {
//        return postMapper.findPostById(postId);
//    }

    @Transactional
    public void createPost(PostRequestDTO postRequestDTO) {
        postMapper.createPost(postRequestDTO);
    }

    @Transactional
    public void updatePost(AddPostRequestDTO addPostRequestDTO) {
        postMapper.updatePost(addPostRequestDTO);
    }
}
