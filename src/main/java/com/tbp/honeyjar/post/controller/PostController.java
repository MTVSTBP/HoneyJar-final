package com.tbp.honeyjar.post.controller;


import com.google.firebase.auth.FirebaseAuthException;
import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.image.service.ImageService;
import com.tbp.honeyjar.post.dto.*;
import com.tbp.honeyjar.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    public PostController(PostService postService, CategoryService categoryService, ImageService imageService) {
        this.postService = postService;
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
    public String getPostDetail(@RequestParam Long postId, Model model) {
        PostResponseDTO post = postService.findPostById(postId);
        model.addAttribute("post", post);
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




//    @PostMapping("/correction")
//    public String postCorrection(AddPostRequestDTO addPostRequestDTO) {
//        postService.updatePost(addPostRequestDTO);
//        return "redirect:/post/detail?postId=" + addPostRequestDTO.getPostId();
//    }

//    @GetMapping("/map")
//    public String findAddress() {
//        return "pages/post/findAddress";
//    }


    @GetMapping("/map")
    public String findAddress(@RequestParam(required = false) String source, @RequestParam(required = false) Long postId, Model model) {
        model.addAttribute("source", source != null ? source : "write");
        if (postId != null) {
            model.addAttribute("postId", postId);
        }
        return "pages/post/findAddress";
    }
}
