package com.tbp.honeyjar.post.controller;


import com.google.firebase.auth.FirebaseAuthException;
import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.image.service.ImageService;
import com.tbp.honeyjar.login.entity.user.User;
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
    private final UserService userService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    public PostController(PostService postService, UserService userService, CategoryService categoryService, ImageService imageService) {
        this.postService = postService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.imageService = imageService;
    }

    @GetMapping
    public String postList(Model model, @RequestParam(required = false) Long selectedCategory) {
        List<PostListDTO> posts = postService.findAllPost();
        model.addAttribute("posts", posts);

        // 카테고리 목록 추가
        model.addAttribute("categories", categoryService.findAllFoodCategory());
        model.addAttribute("selectedCategory", selectedCategory);

        return "pages/post/post";
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
            @RequestParam("mainImageUrl") String mainImageUrl) throws IOException {

        Map<String, Object> response = new HashMap<>();
        try {
            Long postId = postService.createPost(postRequestDTO, files, mainImageFile, mainImageUrl);
            response.put("postId", postId);
            return ResponseEntity.ok(response);
        } catch (FirebaseAuthException e) {
            response.put("error", "Failed to upload file to Firebase: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/detail")
    public String getPostDetail(@RequestParam Long postId, Principal principal, Model model) {

        PostResponseDTO post = postService.findPostById(postId);
        Long userId = userService.findByKakaoId(principal.getName()).getUserId();

        model.addAttribute("post", post);
        model.addAttribute("userId", userId);
        return "pages/post/postDetail";
    }

    @PostMapping("/like/{postId}")
    public void postLike(@PathVariable Long postId, Principal principal, PostLikeRequestDto requestDto) {

        PostResponseDTO post = postService.findPostById(postId);
        Long userId = userService.findByKakaoId(principal.getName()).getUserId();

        requestDto.setPostId(post.getPostId());
        requestDto.setUserId(userId);

        postService.likePost(requestDto);
    }

    @DeleteMapping("/like/{postId}")
    public void postUnlike(@PathVariable Long postId, Principal principal, PostLikeRequestDto requestDto) {

        PostResponseDTO post = postService.findPostById(postId);
        Long userId = userService.findByKakaoId(principal.getName()).getUserId();

        requestDto.setPostId(post.getPostId());
        requestDto.setUserId(userId);

        postService.unlikePost(requestDto);
    }

//
//    @GetMapping("/correction")
//    public String postCorrectionForm(@RequestParam Long postId, Model model) {
//        PostResponseDTO post = postService.findPostById(postId);
//        model.addAttribute("post", post);
//        return "pages/post/postCorrection";
//    }

//    @PostMapping("/correction")
//    public String postCorrection(AddPostRequestDTO addPostRequestDTO) {
//        postService.updatePost(addPostRequestDTO);
//        return "redirect:/post/detail?postId=" + addPostRequestDTO.getPostId();
//    }

    @GetMapping("/map")
    public String findAddress() {
        return "pages/post/findAddress";
    }
}
