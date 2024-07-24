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
import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.dto.PostRequestDTO;
import com.tbp.honeyjar.post.dto.PostResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
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

    public List<PostListDTO> findAllPost(Long category) {
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        return postMapper.findAllPost(params);
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

//    public PostRequestDTO convertToPostRequestDTO(PostResponseDTO post) {
//        PostRequestDTO postRequestDTO = new PostRequestDTO();
//        postRequestDTO.setPostId(post.getPostId());
//        postRequestDTO.setTitle(post.getTitle());
//        postRequestDTO.setRecommendMenu(post.getRecommendMenu());
//        postRequestDTO.setPrice(post.getPrice());
//        postRequestDTO.setPost(post.getPost());
//        postRequestDTO.setImageUrls(post.getImageUrls());
//        postRequestDTO.setPlaceId(post.getPlaceId());
//        postRequestDTO.setCategoryId(post.getCategoryId());
//        postRequestDTO.setCreatedAt(post.getCreatedAt());
//        postRequestDTO.setUpdatedAt(post.getUpdatedAt());
//
//        // Main image URL 설정
//        if (post.getImageUrls() != null && !post.getImageUrls().isEmpty()) {
//            postRequestDTO.setMainImageUrl(post.getImageUrls().get(0)); // 예시: 첫 번째 이미지 URL을 메인 이미지로 사용
//        }
//
//        // Place 정보를 포함합니다.
//        if (post.getPlaceId() != null) {
//            PlaceDTO place = new PlaceDTO();
//            place.setPlaceId(post.getPlaceId());
//            place.setName(post.getPlaceName());
//            place.setRoadAddressName(post.getRoadAddressName());
//            place.setxCoordinate(post.getxCoordinate());
//            place.setyCoordinate(post.getyCoordinate());
//            postRequestDTO.setPlace(place);
//        }
//
//        return postRequestDTO;
//    }

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




//    @Transactional
//    public void updatePost(PostRequestDTO postRequestDTO, List<MultipartFile> files, MultipartFile mainImageFile, String mainImageUrl) throws IOException, FirebaseAuthException {
//        // 기존 장소 정보 업데이트
//        PlaceDTO placeDTO = postRequestDTO.getPlace();
//        if (placeDTO != null) {
//            System.out.println("Updating place: " + placeDTO);  // 디버깅용 로그
//            placeService.updatePlace(placeDTO);
//        }
//
//
//
//        // 기존 이미지 정보 가져오기
//        List<ImageDTO> existingImages = imageService.getImagesByPostId(postRequestDTO.getPostId());
//
//        // 기존 이미지 삭제
//        for (ImageDTO image : existingImages) {
//            try {
//                fireBaseService.deleteFile(image.getUrl());
//            } catch (IOException e) {
//                System.out.println("File not found in the bucket: " + image.getUrl());
//            }
//        }
//        imageService.deleteImagesByPostId(postRequestDTO.getPostId());
//
//        // 새로운 메인 이미지 업로드
//        final String mainImageUploadUrl;
//        if (mainImageFile != null && !mainImageFile.isEmpty()) {
//            mainImageUploadUrl = fireBaseService.uploadFile(mainImageFile, UUID.randomUUID().toString());
//        } else {
//            mainImageUploadUrl = postRequestDTO.getMainImageUrl();
//        }
//        postRequestDTO.setMainImageUrl(mainImageUploadUrl);
//        imageService.saveMainImage(mainImageUploadUrl, postRequestDTO.getUserId(), postRequestDTO.getPostId());
//
//        // 새로운 이미지 업로드
//        List<String> imageUrls = new ArrayList<>();
//        if (files != null && !files.isEmpty()) {
//            for (MultipartFile file : files) {
//                String fileName = UUID.randomUUID().toString();
//                String imageUrl = fireBaseService.uploadFile(file, fileName);
//                imageUrls.add(imageUrl);
//            }
//
//            // 메인 이미지 URL을 리스트에서 제외
//            imageUrls = imageUrls.stream()
//                    .filter(imageUrl -> !imageUrl.equals(mainImageUploadUrl))
//                    .collect(Collectors.toList());
//
//            postRequestDTO.setImageUrls(imageUrls);
//            imageService.saveImages(imageUrls, postRequestDTO.getUserId(), postRequestDTO.getPostId());
//        }
//
//        // 포스트 업데이트
//        postMapper.updatePost(postRequestDTO);
//    }

//    @Transactional
//    public void updatePost(PostRequestDTO postRequestDTO, List<MultipartFile> files, MultipartFile mainImageFile, String mainImageUrl) throws IOException, FirebaseAuthException {
//        Long placeId = postRequestDTO.getPlaceId();
//        if (placeId == null) {
//            placeId = postMapper.findPlaceIdByPostId(postRequestDTO.getPostId());
//            if (placeId == null) {
//                throw new IllegalArgumentException("Post ID에 해당하는 placeId가 없습니다.");
//            }
//            postRequestDTO.setPlaceId(placeId);
//        }
//
//        PlaceDTO placeDTO = postRequestDTO.getPlace();
//        if (placeDTO == null) {
//            throw new IllegalArgumentException("PlaceDTO가 null입니다.");
//        }
//        placeDTO.setPlaceId(placeId);
//
//        System.out.println("Updated PlaceDTO: " + placeDTO);
//
//        // 장소 정보 업데이트
//        placeService.updatePlace(placeDTO);
//
//        // 기존 이미지 정보 가져오기
//        List<ImageDTO> existingImages = imageService.getImagesByPostId(postRequestDTO.getPostId());
//
//        // 기존 이미지 삭제
//        for (ImageDTO image : existingImages) {
//            try {
//                fireBaseService.deleteFile(image.getUrl());
//            } catch (IOException e) {
//                System.out.println("File not found in the bucket: " + image.getUrl());
//            }
//        }
//        imageService.deleteImagesByPostId(postRequestDTO.getPostId());
//
//        // 새로운 메인 이미지 업로드
//        final String mainImageUploadUrl;
//        if (mainImageFile != null && !mainImageFile.isEmpty()) {
//            mainImageUploadUrl = fireBaseService.uploadFile(mainImageFile, UUID.randomUUID().toString());
//        } else {
//            mainImageUploadUrl = postRequestDTO.getMainImageUrl();
//        }
//        postRequestDTO.setMainImageUrl(mainImageUploadUrl);
//        imageService.saveMainImage(mainImageUploadUrl, postRequestDTO.getUserId(), postRequestDTO.getPostId());
//
//        // 새로운 이미지 업로드
//        List<String> imageUrls = new ArrayList<>();
//        if (files != null && !files.isEmpty()) {
//            for (MultipartFile file : files) {
//                String fileName = UUID.randomUUID().toString();
//                String imageUrl = fireBaseService.uploadFile(file, fileName);
//                imageUrls.add(imageUrl);
//            }
//
//            // 메인 이미지 URL을 리스트에서 제외
//            imageUrls = imageUrls.stream()
//                    .filter(imageUrl -> !imageUrl.equals(mainImageUploadUrl))
//                    .collect(Collectors.toList());
//
//            postRequestDTO.setImageUrls(imageUrls);
//            imageService.saveImages(imageUrls, postRequestDTO.getUserId(), postRequestDTO.getPostId());
//        }
//
//        // 포스트 업데이트
//        postMapper.updatePost(postRequestDTO);
//    }

//    @Transactional
//    public void updatePost(PostRequestDTO postRequestDTO, List<MultipartFile> files, MultipartFile mainImageFile, String mainImageUrl) throws IOException, FirebaseAuthException {
//        Long placeId = postRequestDTO.getPlaceId();
//        if (placeId == null) {
//            placeId = postMapper.findPlaceIdByPostId(postRequestDTO.getPostId());
//            if (placeId == null) {
//                throw new IllegalArgumentException("Post ID에 해당하는 placeId가 없습니다.");
//            }
//            postRequestDTO.setPlaceId(placeId);
//        }
//
//        PlaceDTO placeDTO = postRequestDTO.getPlace();
//        if (placeDTO == null) {
//            throw new IllegalArgumentException("PlaceDTO가 null입니다.");
//        }
//        placeDTO.setPlaceId(placeId);
//
//        System.out.println("Updated PlaceDTO: " + placeDTO);
//
//        // 장소 정보 업데이트
//        placeService.updatePlace(placeDTO);
//
//        // 기존 이미지 정보 가져오기
//        List<ImageDTO> existingImages = imageService.getImagesByPostId(postRequestDTO.getPostId());
//
//        // 기존 이미지 삭제
//        for (ImageDTO image : existingImages) {
//            try {
//                fireBaseService.deleteFile(image.getUrl());
//            } catch (IOException e) {
//                System.out.println("File not found in the bucket: " + image.getUrl());
//            }
//        }
//        imageService.deleteImagesByPostId(postRequestDTO.getPostId());
//
//        // 새로운 메인 이미지 업로드
//        final String mainImageUploadUrl;
//        if (mainImageFile != null && !mainImageFile.isEmpty()) {
//            mainImageUploadUrl = fireBaseService.uploadFile(mainImageFile, UUID.randomUUID().toString());
//        } else {
//            mainImageUploadUrl = postRequestDTO.getMainImageUrl();
//        }
//        postRequestDTO.setMainImageUrl(mainImageUploadUrl);
//        imageService.saveMainImage(mainImageUploadUrl, postRequestDTO.getUserId(), postRequestDTO.getPostId());
//
//        // 새로운 이미지 업로드
//        List<String> imageUrls = new ArrayList<>();
//        if (files != null && !files.isEmpty()) {
//            for (MultipartFile file : files) {
//                String fileName = UUID.randomUUID().toString();
//                String imageUrl = fireBaseService.uploadFile(file, fileName);
//                imageUrls.add(imageUrl);
//            }
//
//            // 메인 이미지 URL을 리스트에서 제외
//            imageUrls = imageUrls.stream()
//                    .filter(imageUrl -> !imageUrl.equals(mainImageUploadUrl))
//                    .collect(Collectors.toList());
//
//            postRequestDTO.setImageUrls(imageUrls);
//            imageService.saveImages(imageUrls, postRequestDTO.getUserId(), postRequestDTO.getPostId());
//        }
//
//        // 포스트 업데이트
//        postMapper.updatePost(postRequestDTO);
//    }

//    @Transactional
//    public void updatePost(PostRequestDTO postRequestDTO, List<MultipartFile> files, MultipartFile mainImageFile, String mainImageUrl) throws IOException, FirebaseAuthException {
//        Long placeId = postRequestDTO.getPlaceId();
//        if (placeId == null) {
//            placeId = postMapper.findPlaceIdByPostId(postRequestDTO.getPostId());
//            if (placeId == null) {
//                throw new IllegalArgumentException("Post ID에 해당하는 placeId가 없습니다.");
//            }
//            postRequestDTO.setPlaceId(placeId);
//        }
//
//        PlaceDTO placeDTO = postRequestDTO.getPlace();
//        if (placeDTO == null) {
//            throw new IllegalArgumentException("PlaceDTO가 null입니다.");
//        }
//        placeDTO.setPlaceId(placeId);
//
//        System.out.println("Updated PlaceDTO: " + placeDTO);
//
//        // 장소 정보 업데이트
//        placeService.updatePlace(placeDTO);
//
//        // 기존 이미지 정보 가져오기
//        List<ImageDTO> existingImages = imageService.getImagesByPostId(postRequestDTO.getPostId());
//
//        // 기존 이미지 삭제
//        for (ImageDTO image : existingImages) {
//            try {
//                fireBaseService.deleteFile(image.getUrl());
//            } catch (IOException e) {
//                System.out.println("File not found in the bucket: " + image.getUrl());
//            }
//        }
//        imageService.deleteImagesByPostId(postRequestDTO.getPostId());
//
//        // 새로운 메인 이미지 업로드
//        final String mainImageUploadUrl;
//        if (mainImageFile != null && !mainImageFile.isEmpty()) {
//            mainImageUploadUrl = fireBaseService.uploadFile(mainImageFile, UUID.randomUUID().toString());
//        } else {
//            mainImageUploadUrl = postRequestDTO.getMainImageUrl();
//        }
//        postRequestDTO.setMainImageUrl(mainImageUploadUrl);
//        imageService.saveMainImage(mainImageUploadUrl, postRequestDTO.getUserId(), postRequestDTO.getPostId());
//
//        // 새로운 이미지 업로드
//        List<String> imageUrls = new ArrayList<>();
//        if (files != null && !files.isEmpty()) {
//            for (MultipartFile file : files) {
//                String fileName = UUID.randomUUID().toString();
//                String imageUrl = fireBaseService.uploadFile(file, fileName);
//                imageUrls.add(imageUrl);
//            }
//
//            // 메인 이미지 URL을 리스트에서 제외
//            imageUrls = imageUrls.stream()
//                    .filter(imageUrl -> !imageUrl.equals(mainImageUploadUrl))
//                    .collect(Collectors.toList());
//
//            postRequestDTO.setImageUrls(imageUrls);
//            imageService.saveImages(imageUrls, postRequestDTO.getUserId(), postRequestDTO.getPostId());
//        }
//
//        // 포스트 업데이트
//        postMapper.updatePost(postRequestDTO);
//    }

    @Transactional
    public void updatePost(PostRequestDTO postRequestDTO, List<MultipartFile> files, MultipartFile mainImageFile, String mainImageUrl) throws IOException, FirebaseAuthException {
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


        // 여기에 중복 제거 코드 추가
        placeDTO.setName(removeDuplicates(placeDTO.getName()));
        placeDTO.setxCoordinate(removeDuplicates(placeDTO.getxCoordinate()));
        placeDTO.setyCoordinate(removeDuplicates(placeDTO.getyCoordinate()));
        placeDTO.setRoadAddressName(removeDuplicates(placeDTO.getRoadAddressName()));


        System.out.println("Updated PlaceDTO: " + placeDTO);

        // 장소 정보 업데이트
        System.out.println("PlaceDTO name before update: " + placeDTO.getName());
        placeService.updatePlace(placeDTO);
        System.out.println("PlaceDTO name after update: " + placeDTO.getName());

        // 기존 이미지 정보 가져오기
        List<ImageDTO> existingImages = imageService.getImagesByPostId(postRequestDTO.getPostId());

        // 기존 이미지 삭제
        for (ImageDTO image : existingImages) {
            try {
                fireBaseService.deleteFile(image.getUrl());
            } catch (IOException e) {
                System.out.println("File not found in the bucket: " + image.getUrl());
            }
        }
        imageService.deleteImagesByPostId(postRequestDTO.getPostId());

        // 새로운 메인 이미지 업로드
        final String mainImageUploadUrl;
        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            mainImageUploadUrl = fireBaseService.uploadFile(mainImageFile, UUID.randomUUID().toString());
        } else {
            mainImageUploadUrl = postRequestDTO.getMainImageUrl();
        }
        postRequestDTO.setMainImageUrl(mainImageUploadUrl);
        imageService.saveMainImage(mainImageUploadUrl, postRequestDTO.getUserId(), postRequestDTO.getPostId());

        // 새로운 이미지 업로드
        List<String> imageUrls = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
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
            imageService.saveImages(imageUrls, postRequestDTO.getUserId(), postRequestDTO.getPostId());
        }

        // 포스트 업데이트
        postMapper.updatePost(postRequestDTO);
    }

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

}