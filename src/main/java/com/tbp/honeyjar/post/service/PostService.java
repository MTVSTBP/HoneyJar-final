package com.tbp.honeyjar.post.service;


import com.google.firebase.auth.FirebaseAuthException;
import com.tbp.honeyjar.global.Firebase.FireBaseService;

import com.tbp.honeyjar.image.service.ImageService;
import com.tbp.honeyjar.place.dto.PlaceDTO;
import com.tbp.honeyjar.place.service.PlaceService;
import com.tbp.honeyjar.post.dao.PostMapper;
import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.dto.PostRequestDTO;
import com.tbp.honeyjar.post.dto.PostResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class PostService {

    private final PostMapper postMapper;
    private final ImageService imageService;
    private final PlaceService placeService;
    private final FireBaseService fireBaseService;

    public PostService(PostMapper postMapper, ImageService imageService, PlaceService placeService, FireBaseService fireBaseService) {
        this.postMapper = postMapper;
        this.imageService = imageService;
        this.placeService = placeService;
        this.fireBaseService = fireBaseService;
    }

    @Transactional
    public Long createPost(PostRequestDTO postRequestDTO, List<MultipartFile> files, String mainImageUrl) throws IOException {
        // 1. 새로운 장소 등록
        PlaceDTO placeDTO = postRequestDTO.getPlace();
        placeService.createPlace(placeDTO);
        Long placeId = placeDTO.getPlaceId();

        // 2. 포스트 등록 시 placeId를 설정
        postRequestDTO.setPlaceId(placeId);
        postMapper.createPost(postRequestDTO);
        Long postId = postRequestDTO.getPostId();

        // 3. 파일 업로드 및 이미지 URL 리스트 생성
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String fileName = generateFileName(file); // 파일 이름 생성 로직 추가
                String imageUrl = fireBaseService.uploadFile(file, fileName); // Firebase에 파일 업로드
                imageUrls.add(imageUrl);
            } catch (FirebaseAuthException e) {
                // FirebaseAuthException 처리 로직 추가
                e.printStackTrace();
                // 예를 들어, 이미지 업로드 실패시 예외를 다시 던지거나 로그를 남길 수 있습니다.
                throw new RuntimeException("Failed to upload file to Firebase", e);
            }
        }

        postRequestDTO.setImageUrls(imageUrls);
        postRequestDTO.setMainImageUrl(mainImageUrl);

        // 4. 썸네일 이미지를 포함한 모든 이미지를 삽입
        imageService.saveImages(imageUrls, postRequestDTO.getUserId(), postId, postRequestDTO.getMainImageUrl());

        return postId;
    }

    private String generateFileName(MultipartFile file) {
        // 파일 이름 생성 로직을 구현합니다.
        return UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    }

    public List<PostListDTO> findAllPost() {
        return postMapper.findAllPost();
    }

    public PostResponseDTO findPostById(Long postId) {
        return postMapper.findPostById(postId);
    }
}
