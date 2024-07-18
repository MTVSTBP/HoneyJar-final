package com.tbp.honeyjar.post.controller;


import com.tbp.honeyjar.post.dto.*;
import com.tbp.honeyjar.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String postList(Model model) {
        List<PostListDTO> posts = postService.findAllPost();
        model.addAttribute("posts", posts);
        return "pages/post/post";
    }

    @GetMapping("/write")
    public String postCreateForm(Model model) {
        model.addAttribute("postRequestDTO", new PostRequestDTO());
        return "pages/post/postWrite";
    }

    @PostMapping("/write")
    public ResponseEntity<Map<String, Object>> postCreate(
            @ModelAttribute PostRequestDTO postRequestDTO,
            @RequestParam("imageUrls") String imageUrls,
            @RequestParam("mainImageUrl") String mainImageUrl) {
        try {
            // Process the image URLs and main image URL
            postRequestDTO.setImageUrls(Arrays.asList(imageUrls.split(",")));
            postRequestDTO.setMainImageUrl(mainImageUrl);

            Long postId = postService.createPost(postRequestDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Post created successfully");
            response.put("postId", postId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Failed to create post");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/detail")
    public String postDetail(@RequestParam Long postId, Model model) {
        PostResponseDTO post = postService.findPostById(postId);
        model.addAttribute("post", post);
        return "pages/post/postDetail";
    }

    @GetMapping("/correction")
    public String postCorrectionForm(@RequestParam Long postId, Model model) {
        PostResponseDTO post = postService.findPostById(postId);
        model.addAttribute("post", post);
        return "pages/post/postCorrection";
    }

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
