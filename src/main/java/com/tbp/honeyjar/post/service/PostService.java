package com.tbp.honeyjar.post.service;



import com.tbp.honeyjar.image.dto.ImageDTO;
import com.tbp.honeyjar.image.service.ImageService;
import com.tbp.honeyjar.post.dao.PostMapper;

import com.tbp.honeyjar.post.dto.AddPostRequestDTO;
import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.dto.PostRequestDTO;
import com.tbp.honeyjar.post.dto.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final ImageService imageService;

    public List<PostListDTO> findAllPost() {
        return postMapper.findAllPost();
    }

    public PostResponseDTO findPostById(Long postId) {
        PostResponseDTO post = postMapper.findPostById(postId);
        List<ImageDTO> images = imageService.getImagesByPostId(postId);

        List<String> imageUrls = images.stream()
                .map(ImageDTO::getUrl)
                .collect(Collectors.toList());
        post.setImageUrls(imageUrls);

        String mainImageUrl = images.stream()
                .filter(ImageDTO::isMain)
                .map(ImageDTO::getUrl)
                .findFirst()
                .orElse(null);
        post.setMainImageUrl(mainImageUrl);

        return post;
    }

    @Transactional
    public Long createPost(PostRequestDTO postRequestDTO) {
        postMapper.createPost(postRequestDTO);
        Long postId = Optional.ofNullable(postRequestDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Failed to create post: postId is null"));
        imageService.saveImages(postRequestDTO.getImageUrls(), postRequestDTO.getUserId(), postId, postRequestDTO.getMainImageUrl());
        return postId;
    }

    @Transactional
    public void updatePost(AddPostRequestDTO addPostRequestDTO) {
        postMapper.updatePost(addPostRequestDTO);
        // 필요시 이미지 업데이트 로직 추가
    }
}
