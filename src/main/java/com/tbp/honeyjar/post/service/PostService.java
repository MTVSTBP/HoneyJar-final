package com.tbp.honeyjar.post.service;



import com.tbp.honeyjar.image.dao.ImageMapper;
import com.tbp.honeyjar.image.dto.ImageDTO;
import com.tbp.honeyjar.image.service.ImageService;
import com.tbp.honeyjar.place.dao.PlaceMapper;
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
public class PostService {

    private final PostMapper postMapper;
    private final ImageMapper imageMapper;
    private final PlaceMapper placeMapper;

    public PostService(PostMapper postMapper, ImageMapper imageMapper, PlaceMapper placeMapper) {
        this.postMapper = postMapper;
        this.imageMapper = imageMapper;
        this.placeMapper = placeMapper;
    }

    @Transactional
    public Long createPost(PostRequestDTO postRequestDTO) {
        postMapper.createPost(postRequestDTO);
        Long postId = postRequestDTO.getPostId();
        List<String> imageUrls = postRequestDTO.getImageUrls();

        // 썸네일 이미지를 포함한 모든 이미지를 삽입
        List<ImageDTO> images = imageUrls.stream()
                .map(url -> new ImageDTO(null, url, postRequestDTO.getUserId(), postId, url.equals(postRequestDTO.getMainImageUrl())))
                .collect(Collectors.toList());

        imageMapper.insertImages(images);

        return postId;
    }

    public List<PostListDTO> findAllPost() {
        return postMapper.findAllPost();
    }

    public PostResponseDTO findPostById(Long postId) {
        return postMapper.findPostById(postId);
    }
}