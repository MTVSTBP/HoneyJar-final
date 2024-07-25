package com.tbp.honeyjar.post.service;


import com.google.firebase.auth.FirebaseAuthException;
import com.tbp.honeyjar.admin.dao.CategoryMapper;
import com.tbp.honeyjar.admin.dto.category.FoodResponseDto;
import com.tbp.honeyjar.global.Firebase.FireBaseService;

import com.tbp.honeyjar.image.dto.ImageDTO;
import com.tbp.honeyjar.image.service.ImageService;
import com.tbp.honeyjar.place.dto.PlaceDTO;
import com.tbp.honeyjar.place.service.PlaceService;
import com.tbp.honeyjar.post.dao.PostMapper;
import com.tbp.honeyjar.post.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class PostService {

    private final PostMapper postMapper;
    private final ImageService imageService;
    private final PlaceService placeService;
    private final FireBaseService fireBaseService;
    private final CategoryMapper categoryMapper;

    public PostService(PostMapper postMapper, ImageService imageService, PlaceService placeService, FireBaseService fireBaseService, CategoryMapper categoryMapper) {
        this.postMapper = postMapper;
        this.imageService = imageService;
        this.placeService = placeService;
        this.fireBaseService = fireBaseService;
        this.categoryMapper = categoryMapper;
    }


    @Transactional
    public Long createPost(PostRequestDTO postRequestDTO, List<MultipartFile> files, MultipartFile mainImageFile, String mainImageUrl) throws IOException, FirebaseAuthException {
        // 새로운 장소 등록
        PlaceDTO placeDTO = postRequestDTO.getPlace();
        placeService.createPlace(placeDTO);
        Long placeId = placeDTO.getPlaceId();

        // 포스트 등록 시 placeId를 설정
        postRequestDTO.setPlaceId(placeId);
        postMapper.createPost(postRequestDTO);
        Long postId = postRequestDTO.getPostId();

        // 메인 이미지 업로드
        String mainImageUploadUrl = fireBaseService.uploadFile(mainImageFile, UUID.randomUUID().toString());

        // 파일 업로드 및 이미지 URL 리스트 생성
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString();
            String imageUrl = fireBaseService.uploadFile(file, fileName);
            imageUrls.add(imageUrl);
        }

        // 메인 이미지 URL을 리스트에서 제외
        imageUrls = imageUrls.stream()
                .filter(imageUrl -> !imageUrl.equals(mainImageUploadUrl))
                .collect(Collectors.toList());

        postRequestDTO.setImageUrls(imageUrls);
        postRequestDTO.setMainImageUrl(mainImageUploadUrl);

        // 썸네일 이미지를 포함한 모든 이미지를 삽입
        imageService.saveMainImage(mainImageUploadUrl, postRequestDTO.getUserId(), postId);
        imageService.saveImages(imageUrls, postRequestDTO.getUserId(), postId);

        return postId;
    }



    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    }

    public List<PostListDTO> findAllPost() {
        return postMapper.findAllPost();
    }

    public PostResponseDTO findPostById(Long postId) {
        PostResponseDTO postResponseDTO = postMapper.findPostById(postId);
        Long categoryId = postResponseDTO.getCategoryId();
        if (categoryId != null) {
            FoodResponseDto category = categoryMapper.findFoodById(categoryId);
            if (category != null) {
                postResponseDTO.setCategoryName(category.getName());
            }
        }
        return postResponseDTO;
    }

    public void likePost(PostLikeRequestDto requestDto) {
        postMapper.likePost(requestDto);
    }

    public void unlikePost(PostLikeRequestDto requestDto) {
        postMapper.unlikePost(requestDto);
    }

    public int getLikeCountByPostId(Long postId) {
        return postMapper.getLikeCountByPostId(postId);
    }

    public boolean getIsLikedByPostIdAndUserId(Long postId, Long userId) {
        return postMapper.getIsLikedByPostIdAndUserId(postId, userId) == 1;
    }

    public float getRating(Long postId) {
        return postMapper.getRating(postId);
    }

    public void rating(PostRatingRequestDto requestDto) {
        postMapper.rating(requestDto);
    }

    public boolean getIsRatedByPostIdAndUserId(Long postId, Long userId) {
        return postMapper.getIsRatedByPostIdAndUserId(postId, userId) == 1;
    }

    public void ratingAgain(PostRatingRequestDto requestDto) {
        postMapper.ratingAgain(requestDto);
    }
}
