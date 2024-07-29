package com.tbp.honeyjar.post.controller;


import com.google.firebase.auth.FirebaseAuthException;
import com.google.gson.Gson;
import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.image.service.ImageService;
import com.tbp.honeyjar.login.service.user.UserService;
import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.service.MyPageService;
import com.tbp.honeyjar.place.dto.PlaceDTO;
import com.tbp.honeyjar.place.service.PlaceService;
import com.tbp.honeyjar.post.dto.*;
import com.tbp.honeyjar.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;
    private final PlaceService placeService;
    private final ImageService imageService;
    private final UserService userService;
    private final MyPageService myPageService;


    public PostController(PostService postService, CategoryService categoryService, PlaceService placeService, ImageService imageService, UserService userService, MyPageService myPageService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.placeService = placeService;
        this.imageService = imageService;
        this.userService = userService;
        this.myPageService = myPageService;
    }

    @GetMapping
    public String postList(Model model, @RequestParam(required = false) Long category, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size, @RequestParam(required = false) boolean goodRestaurant, @RequestParam(required = false) String sortOption, @RequestParam(required = false) Double latitude, @RequestParam(required = false) Double longitude, Principal principal) {

        Long userId = null;
        if (principal != null) {
            userId = userService.findUserIdByKakaoId(principal.getName());
        }

        Integer maxPrice = goodRestaurant ? 10000 : null;
        List<PostListDTO> posts = postService.findPostsByCategory(category, page, size, userId, maxPrice, sortOption, latitude, longitude);

        // MyPageDTO 객체 생성 (또는 서비스에서 가져오기)
        MyPageDTO myPage = myPageService.getMyPage(userId);
        model.addAttribute("myPage", myPage);

        // 최초 요청 시 4개의 포스트만 반환
        if (page == 0) {
            model.addAttribute("posts", posts);
            model.addAttribute("categories", categoryService.findAllFoodCategory());
            model.addAttribute("selectedCategory", category);
            model.addAttribute("goodRestaurant", goodRestaurant);
            return "pages/post/post"; // 전체 포스트 페이지
        }

        // AJAX 요청일 경우 특정 페이지의 포스트만 반환
        model.addAttribute("posts", posts);
        return "common/components/postComponent"; // 포스트 컴포넌트를 반환
    }


    @GetMapping("/write")
    public String postCreateForm(Model model) {
        model.addAttribute("postRequestDTO", new PostRequestDTO());
        model.addAttribute("categories", categoryService.findAllFoodCategory()); // 카테고리 목록 추가
        return "pages/post/postWrite";
    }


    @PostMapping("/write")
    public ResponseEntity<Map<String, Object>> postCreate(@ModelAttribute PostRequestDTO postRequestDTO, @RequestParam(value = "files", required = false) List<MultipartFile> files, @RequestParam("mainImageFile") MultipartFile mainImageFile, @RequestParam("mainImageUrl") String mainImageUrl, Principal principal) throws IOException {

        Map<String, Object> response = new HashMap<>();
        try {
            Long userId = userService.findUserIdByKakaoId(principal.getName());
            postRequestDTO.setUserId(userId); // userId 설정

            Long postId = postService.createPost(postRequestDTO, files, mainImageFile, mainImageUrl);
            response.put("postId", postId);
            return ResponseEntity.ok(response);
        } catch (FirebaseAuthException e) {
            response.put("error", "Failed to upload file to Firebase: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/{postId}")
    public ResponseEntity<PostRequestDTO> getPost(@PathVariable Long postId, Principal principal) {
        Long userId = null;
        if (principal != null) {
            userId = userService.findUserIdByKakaoId(principal.getName());
        }
        PostResponseDTO postResponseDTO = postService.findPostById(postId, userId);
        PostRequestDTO postRequestDTO = postService.convertToPostRequestDTO(postResponseDTO);
        postRequestDTO.setExistingImageUrls(postResponseDTO.getImageUrls());
        postRequestDTO.setThumbnailIndex(postResponseDTO.getThumbnailIndex());
        return ResponseEntity.ok(postRequestDTO);
    }


    @GetMapping("/detail")
    public String getPostDetail(@RequestParam Long postId, Model model, Principal principal) {
        Long loggedInUserId = userService.findUserIdByKakaoId(principal.getName()); // 로그인된 사용자의 userId를 가져옴
        PostResponseDTO post = postService.findPostById(postId, loggedInUserId); // userId를 추가로 전달
        MyPageDTO myPage = myPageService.getMyPage(loggedInUserId);
        int commentCnt = postService.commentCount(postId);

        boolean isAuthor = false;
        if (post.getUserId() != null) {
            isAuthor = post.getUserId().equals(loggedInUserId);
        }

        if (loggedInUserId != null) {
            int likeCount = postService.getLikeCountByPostId(postId);
            boolean isLiked = postService.getIsLikedByPostIdAndUserId(postId, loggedInUserId);
            float rating = postService.getRating(postId);
            boolean isRated = postService.getIsRatedByPostIdAndUserId(postId, loggedInUserId);

            // 디버깅을 위한 로그 추가
            System.out.println("Post UserId: " + post.getUserId());
            System.out.println("Logged in UserId: " + loggedInUserId);
            System.out.println("Is Author: " + isAuthor);

            model.addAttribute("myPage", myPage);
            model.addAttribute("post", post);
            model.addAttribute("isAuthor", isAuthor); // 작성자인지 여부를 모델에 추가
            model.addAttribute("userId", loggedInUserId);
            model.addAttribute("likeCount", likeCount);
            model.addAttribute("isLiked", isLiked);
            model.addAttribute("rating", rating);
            model.addAttribute("isRated", isRated);
            model.addAttribute("commentCnt", commentCnt);
        }

        return "pages/post/postDetail";
    }

    @PostMapping("/like/{postId}")
    @ResponseBody
    public void postLike(@PathVariable Long postId, Principal principal, PostLikeRequestDto requestDto) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        PostResponseDTO post = postService.findPostById(postId, userId);


        if (post != null && userId != null) {
            requestDto.setPostId(post.getPostId());
            requestDto.setUserId(userId);

            postService.likePost(requestDto);
        }
    }

    @DeleteMapping("/like/{postId}")
    @ResponseBody
    public void postUnlike(@PathVariable Long postId, Principal principal, PostLikeRequestDto requestDto) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        PostResponseDTO post = postService.findPostById(postId, userId);


        if (post != null && userId != null) {
            requestDto.setPostId(post.getPostId());
            requestDto.setUserId(userId);

            postService.unlikePost(requestDto);
        }
    }

    @PostMapping("/rating/{postId}")
    @ResponseBody
    public void postRating(@PathVariable Long postId, Principal principal, @RequestBody PostRatingRequestDto requestDto) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        PostResponseDTO post = postService.findPostById(postId, userId);


        if (post != null && userId != null) {
            requestDto.setPostId(post.getPostId());
            requestDto.setUserId(userId);

            postService.rating(requestDto);
        }
    }

    @PostMapping("/rating-again/{postId}")
    @ResponseBody
    public void postRatingAgain(@PathVariable Long postId, Principal principal, @RequestBody PostRatingRequestDto requestDto) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        PostResponseDTO post = postService.findPostById(postId, userId);


        if (post != null && userId != null) {
            requestDto.setPostId(post.getPostId());
            requestDto.setUserId(userId);

            postService.ratingAgain(requestDto);
        }
    }

    @GetMapping("/correction")
    public String postCorrectionForm(@RequestParam Long postId, Model model, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        PostResponseDTO post = postService.findPostById(postId, userId);
        PostRequestDTO postRequestDTO = postService.convertToPostRequestDTO(post);
        postRequestDTO.setExistingImageUrls(post.getImageUrls()); // 기존 이미지 URL 설정

        // 기존 이미지 URL 리스트를 JSON 형식으로 변환하여 모델에 추가
        String existingImageUrlsJson = new Gson().toJson(postRequestDTO.getExistingImageUrls());
        model.addAttribute("existingImageUrlsJson", existingImageUrlsJson);

        model.addAttribute("postRequestDTO", postRequestDTO);
        model.addAttribute("categories", categoryService.findAllFoodCategory()); // 카테고리 목록 추가
        model.addAttribute("userId", userId); // userId를 모델에 추가

        return "pages/post/postCorrection";
    }

    @PostMapping("/correction")
    public ResponseEntity<?> postCorrection(@ModelAttribute PostRequestDTO postRequestDTO, @RequestParam(value = "files", required = false) List<MultipartFile> files, @RequestParam("mainImageFile") MultipartFile mainImageFile, @RequestParam("mainImageUrl") String mainImageUrl, @RequestParam(value = "existingImageUrls", required = false) List<String> existingImageUrls, @RequestParam(value = "deletedImages", required = false) List<String> deletedImages, Principal principal) throws IOException, FirebaseAuthException {
        Long loggedInUserId = userService.findUserIdByKakaoId(principal.getName());
        PostResponseDTO existingPost = postService.findPostById(postRequestDTO.getPostId(), loggedInUserId);

        if (existingPost.getUserId() == null || !existingPost.getUserId().equals(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 권한이 없습니다.");
        }

        // 빈 파일 리스트 처리
        if (files == null) {
            files = new ArrayList<>();
        }

        // existingImageUrls가 null인 경우 빈 리스트로 초기화합니다.
        if (existingImageUrls == null) {
            existingImageUrls = new ArrayList<>();
        }
        postRequestDTO.setExistingImageUrls(existingImageUrls);

        // deletedImages가 null인 경우 빈 리스트로 초기화합니다.
        if (deletedImages == null) {
            deletedImages = new ArrayList<>();
        }

        // userId를 DTO에 설정합니다.
        postRequestDTO.setUserId(loggedInUserId);

        postService.updatePost(postRequestDTO, files, mainImageFile, mainImageUrl, deletedImages);

        Map<String, Object> response = new HashMap<>();
        response.put("postId", postRequestDTO.getPostId());
        response.put("message", "Post updated successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/map")
    public String findAddress(@RequestParam(required = false) String redirectTo, Model model) {
        if (redirectTo != null) {
            model.addAttribute("redirectTo", redirectTo);
        }
        return "pages/post/findAddress";
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> softDeletePost(@PathVariable Long postId, Principal principal) {
        Long loggedInUserId = userService.findUserIdByKakaoId(principal.getName());
        PostResponseDTO existingPost = postService.findPostById(postId, loggedInUserId);

        if (existingPost.getUserId() == null || !existingPost.getUserId().equals(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
        }

        postService.softDeletePost(postId);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/by-place")
    public String getPostsByPlace(@RequestParam String placeName, Model model) {
        List<PostListDTO> posts = postService.findPostsByPlaceName(placeName);
        model.addAttribute("posts", posts);
        model.addAttribute("placeName", placeName);
        // 필터링 폼에 필요한 속성들은 null로 설정
//        model.addAttribute("categories", null);
//        model.addAttribute("selectedCategory", null);
//        model.addAttribute("goodRestaurant", null);
        return "pages/post/post";
    }

    @GetMapping("/coordinates")
    @ResponseBody
    public List<Map<String, Double>> getAllPostCoordinates() {
        return postService.findAllPostCoordinates();
    }

    @GetMapping("/info")
    @ResponseBody
    public Map<String, Object> getPlaceInfo(@RequestParam Double lat, @RequestParam Double lng) {
        return postService.getPlaceInfoByCoordinates(lat, lng);
    }

    @GetMapping("/search")
    @ResponseBody
    public List<PostResponseDTO> searchPosts(@RequestParam String keyword) {
        return postService.searchPostsByPlaceName(keyword);
    }
}
