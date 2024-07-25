package com.tbp.honeyjar.post.controller;


import com.google.firebase.auth.FirebaseAuthException;
import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.image.service.ImageService;
import com.tbp.honeyjar.login.service.user.UserService;
import com.tbp.honeyjar.post.dto.*;
import com.tbp.honeyjar.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;


@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final UserService userService;


    public PostController(PostService postService, CategoryService categoryService, ImageService imageService, UserService userService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping
    public String postList(Model model,
                           @RequestParam(required = false) Long category,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "6") int size) {
        // 최초 요청 시 4개의 포스트만 반환
        if (page == 0) {
            List<PostListDTO> posts = postService.findPostsByCategory(category, 0, 6); // 4개만 가져오기
            model.addAttribute("posts", posts);
            model.addAttribute("categories", categoryService.findAllFoodCategory());
            model.addAttribute("selectedCategory", category);
            return "pages/post/post"; // 전체 포스트 페이지
        }

        // AJAX 요청일 경우 특정 페이지의 포스트만 반환
        List<PostListDTO> posts = postService.findPostsByCategory(category, page, size);
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
    public ResponseEntity<Map<String, Object>> postCreate(
            @ModelAttribute PostRequestDTO postRequestDTO,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("mainImageFile") MultipartFile mainImageFile,
            @RequestParam("mainImageUrl") String mainImageUrl,
            Principal principal) throws IOException {

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



    @GetMapping("/detail")
    public String getPostDetail(@RequestParam Long postId, Model model, Principal principal) {
        PostResponseDTO post = postService.findPostById(postId);
        Long loggedInUserId = userService.findUserIdByKakaoId(principal.getName()); // 로그인된 사용자의 userId를 가져옴

        boolean isAuthor = false;
        if (post.getUserId() != null) {
            isAuthor = post.getUserId().equals(loggedInUserId);
        }

        // 디버깅을 위한 로그 추가
        System.out.println("Post UserId: " + post.getUserId());
        System.out.println("Logged in UserId: " + loggedInUserId);
        System.out.println("Is Author: " + isAuthor);

        model.addAttribute("post", post);
        model.addAttribute("isAuthor", isAuthor); // 작성자인지 여부를 모델에 추가
        return "pages/post/postDetail";
    }


    @GetMapping("/correction")
    public String postCorrectionForm(@RequestParam Long postId, Model model) {
        PostResponseDTO post = postService.findPostById(postId);
        PostRequestDTO postRequestDTO = postService.convertToPostRequestDTO(post);
        model.addAttribute("postRequestDTO", postRequestDTO);
        model.addAttribute("categories", categoryService.findAllFoodCategory()); // 카테고리 목록 추가
        return "pages/post/postCorrection";
    }

    @PostMapping("/correction")
    public ResponseEntity<?> postCorrection(@ModelAttribute PostRequestDTO postRequestDTO,
                                            @RequestParam("files") List<MultipartFile> files,
                                            @RequestParam("mainImageFile") MultipartFile mainImageFile,
                                            @RequestParam("mainImageUrl") String mainImageUrl,
                                            Principal principal) throws IOException, FirebaseAuthException {
        Long loggedInUserId = userService.findUserIdByKakaoId(principal.getName());
        PostResponseDTO existingPost = postService.findPostById(postRequestDTO.getPostId());

        if (existingPost.getUserId() == null || !existingPost.getUserId().equals(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 권한이 없습니다.");
        }

        postService.updatePost(postRequestDTO, files, mainImageFile, mainImageUrl);
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
        PostResponseDTO existingPost = postService.findPostById(postId);

        if (existingPost.getUserId() == null || !existingPost.getUserId().equals(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
        }

        postService.softDeletePost(postId);
        return ResponseEntity.ok("Post deleted successfully");
    }
}

