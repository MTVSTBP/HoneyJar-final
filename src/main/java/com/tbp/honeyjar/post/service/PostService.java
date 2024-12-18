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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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

    public List<PostListDTO> findPostsByCategory(Long category, int page, int size, Long userId, Integer maxPrice, String sortOption, Double latitude, Double longitude) {
        int offset = page * size;
        return postMapper.findPostsByCategory(category, size, offset, userId, maxPrice, sortOption, latitude, longitude);
    }


    @Transactional
    public Long createPost(PostRequestDTO postRequestDTO, List<MultipartFile> files, MultipartFile mainImageFile, String mainImageUrl) throws IOException, FirebaseAuthException {
        // 새로운 장소 등록
        PlaceDTO placeDTO = postRequestDTO.getPlace();

        // 좌표 값을 반올림하여 설정
        placeDTO.setxCoordinate(roundCoordinate(placeDTO.getxCoordinate()));
        placeDTO.setyCoordinate(roundCoordinate(placeDTO.getyCoordinate()));

        // road_address_name 값의 앞에 쉼표를 제거
        placeDTO.setRoadAddressName(removeLeadingComma(placeDTO.getRoadAddressName()));


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
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) { // 빈 파일인지 확인
                    String fileName = UUID.randomUUID().toString();
                    String imageUrl = fireBaseService.uploadFile(file, fileName);
                    imageUrls.add(imageUrl);
                }
            }

            // 메인 이미지 URL을 리스트에서 제외
            imageUrls = imageUrls.stream()
                    .filter(imageUrl -> !imageUrl.equals(mainImageUploadUrl))
                    .collect(Collectors.toList());
        }

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


    public PostResponseDTO findPostById(Long postId, Long userId) {
        PostResponseDTO postResponseDTO = postMapper.findPostById(postId, userId);
        // 카테고리 이름 설정
        Long categoryId = postResponseDTO.getCategoryId();
        if (categoryId != null) {
            FoodResponseDto category = categoryMapper.findFoodById(categoryId);
            if (category != null) {
                postResponseDTO.setCategoryName(category.getName());
            }
        }

        // 메인 이미지 URL 가져오기 (예시: mainImageUrl 필드를 데이터베이스에서 가져옴)
        String mainImageUrl = postResponseDTO.getMainImageUrl();
        postResponseDTO.setMainImageUrl(mainImageUrl);

        // 썸네일 인덱스 계산
        if (postResponseDTO.getImageUrls() != null) {
            int thumbnailIndex = postResponseDTO.getImageUrls().indexOf(mainImageUrl);
            postResponseDTO.setThumbnailIndex(thumbnailIndex);
        }

        return postResponseDTO;
    }


    public PostRequestDTO convertToPostRequestDTO(PostResponseDTO post) {
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setPostId(post.getPostId());
        postRequestDTO.setTitle(post.getTitle());
        postRequestDTO.setRecommendMenu(post.getRecommendMenu());
        postRequestDTO.setPrice(post.getPrice());
        postRequestDTO.setPost(post.getPost());
        postRequestDTO.setImageUrls(post.getImageUrls());
        postRequestDTO.setPlaceId(post.getPlaceId());
        postRequestDTO.setCategoryId(post.getCategoryId());
        postRequestDTO.setCreatedAt(post.getCreatedAt());
        postRequestDTO.setUpdatedAt(post.getUpdatedAt());
        postRequestDTO.setMainImageUrl(post.getMainImageUrl());

        // Main image URL 설정
        if (post.getImageUrls() != null && !post.getImageUrls().isEmpty()) {
            postRequestDTO.setMainImageUrl(post.getImageUrls().get(0));
        }

        // Place 정보를 포함합니다.
        if (post.getPlaceId() != null) {
            PlaceDTO place = new PlaceDTO();
            place.setPlaceId(post.getPlaceId()); // 이 부분에서 placeId를 설정합니다.
            place.setName(post.getPlaceName());
            place.setRoadAddressName(post.getRoadAddressName());
            place.setxCoordinate(post.getxCoordinate());
            place.setyCoordinate(post.getyCoordinate());
            postRequestDTO.setPlace(place);
        }

        return postRequestDTO;
    }

    @Transactional
    public void updatePost(PostRequestDTO postRequestDTO, List<MultipartFile> files, MultipartFile mainImageFile, String mainImageUrl, List<String> deletedImages) throws IOException, FirebaseAuthException {
        Long placeId = postRequestDTO.getPlaceId();
        if (placeId == null) {
            placeId = postMapper.findPlaceIdByPostId(postRequestDTO.getPostId());
            if (placeId == null) {
                throw new IllegalArgumentException("Post ID에 해당하는 placeId가 없습니다.");
            }
            postRequestDTO.setPlaceId(placeId);
        }

        PlaceDTO placeDTO = postRequestDTO.getPlace();
        if (placeDTO == null) {
            throw new IllegalArgumentException("PlaceDTO가 null입니다.");
        }
        placeDTO.setPlaceId(placeId);

        placeDTO.setRoadAddressName(removeLeadingComma(placeDTO.getRoadAddressName()));
        placeDTO.setName(removeDuplicates(placeDTO.getName()));
        placeDTO.setxCoordinate(removeDuplicateCoordinates(placeDTO.getxCoordinate()));
        placeDTO.setyCoordinate(removeDuplicateCoordinates(placeDTO.getyCoordinate()));
        placeDTO.setRoadAddressName(removeDuplicates(placeDTO.getRoadAddressName()));

        placeService.updatePlace(placeDTO);

        List<ImageDTO> existingImages = imageService.getImagesByPostId(postRequestDTO.getPostId());

        // Initialize existingImageUrls to an empty list if null
        if (postRequestDTO.getExistingImageUrls() == null) {
            postRequestDTO.setExistingImageUrls(new ArrayList<>());
        }

        // 삭제된 이미지 처리
        for (ImageDTO image : existingImages) {
            if (!postRequestDTO.getExistingImageUrls().contains(image.getUrl()) || deletedImages.contains(image.getUrl())) {
                try {
                    fireBaseService.deleteFile(image.getUrl());
                } catch (IOException e) {
                    System.out.println("File not found in the bucket: " + image.getUrl());
                }
                imageService.deleteImageById(image.getImageId());
            }
        }

        for (ImageDTO image : existingImages) {
            if (image.isMain()) {
                imageService.updateMainImageStatus(image.getImageId(), false);
            }
        }

        final String mainImageUploadUrl;
        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            mainImageUploadUrl = fireBaseService.uploadFile(mainImageFile, UUID.randomUUID().toString());
        } else {
            mainImageUploadUrl = postRequestDTO.getMainImageUrl();
        }
        postRequestDTO.setMainImageUrl(mainImageUploadUrl);
        imageService.saveMainImage(mainImageUploadUrl, postRequestDTO.getUserId(), postRequestDTO.getPostId());

        List<String> imageUrls = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                String fileName = UUID.randomUUID().toString();
                String imageUrl = fireBaseService.uploadFile(file, fileName);
                imageUrls.add(imageUrl);
            }
            imageUrls = imageUrls.stream()
                    .filter(imageUrl -> !imageUrl.equals(mainImageUploadUrl))
                    .collect(Collectors.toList());

            postRequestDTO.setImageUrls(imageUrls);
            imageService.saveImages(imageUrls, postRequestDTO.getUserId(), postRequestDTO.getPostId());
        }

        ImageDTO newMainImage = imageService.getImagesByPostId(postRequestDTO.getPostId())
                .stream()
                .filter(image -> image.getUrl().equals(mainImageUploadUrl))
                .findFirst()
                .orElse(null);

        if (newMainImage != null) {
            imageService.updateMainImageStatus(newMainImage.getImageId(), true);
        }

        postMapper.updatePost(postRequestDTO);
    }


    // 좌표 값을 소수점 이하 7자리로 반올림하는 메서드
    private Double roundCoordinate(Double coordinate) {
        return Math.round(coordinate * 1e7) / 1e7;
    }

    // 중복 좌표 값을 제거하고 평균 값을 반환하는 메서드
    private Double removeDuplicateCoordinates(Double coordinate) {
        // 중복 값을 제거하고 소수점 이하 7자리로 반올림하여 반환
        return roundCoordinate(coordinate);
    }

    // road_address_name 값의 앞에 쉼표를 제거하는 메서드
    private String removeLeadingComma(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("^,\\s*", "");
    }

    // 기존 문자열 중복 제거 메서드
    private String removeDuplicates(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        String[] parts = value.split(",");
        return Arrays.stream(parts).distinct().collect(Collectors.joining(","));
    }


    @Transactional
    public void softDeletePost(Long postId) {
        postMapper.softDeletePost(postId);
    }

    public int commentCount(Long postId) {
        return postMapper.commentCount(postId);
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

    public List<Map<String, Double>> findAllPostCoordinates() {
        return postMapper.findAllPostCoordinates();
    }

    public List<PostListDTO> findPostsByPlaceName(String placeName) {
        return postMapper.findPostsByPlaceName(placeName);
    }

    public Map<String, Object> getPlaceInfoByCoordinates(double latitude, double longitude) {
        List<PostResponseDTO> posts = postMapper.findPostsByCoordinates(latitude, longitude);
        Map<String, Object> result = new HashMap<>();

        if (!posts.isEmpty()) {
            result.put("placeName", posts.get(0).getPlaceName());
            result.put("postCount", posts.size());
            if (posts.size() == 1) {
                result.put("postId", posts.get(0).getPostId());
            }
        } else {
            result.put("postCount", 0);
        }

        return result;
    }

    public List<PostResponseDTO> searchPostsByPlaceName(String keyword) {
        List<PostResponseDTO> posts = postMapper.searchPostsByPlaceName(keyword);
        Map<String, List<PostResponseDTO>> postsByPlace = posts.stream()
                .collect(Collectors.groupingBy(PostResponseDTO::getPlaceName));

        return postsByPlace.entrySet().stream()
                .map(entry -> {
                    PostResponseDTO representative = entry.getValue().get(0);
                    representative.setPostCount(entry.getValue().size());
                    return representative;
                })
                .collect(Collectors.toList());
    }
}