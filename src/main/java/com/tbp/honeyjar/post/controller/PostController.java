package com.tbp.honeyjar.post.controller;


import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.post.dto.*;
import com.tbp.honeyjar.post.service.PostService;
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

    public PostController(PostService postService, CategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;
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
//        model.addAttribute("categories", categoryService.findAllFoodCategory()); // 카테고리 목록 추가
        return "pages/post/postWrite";
    }



    @PostMapping("/write")
    public ResponseEntity<Map<String, Object>> postCreate(
            @ModelAttribute PostRequestDTO postRequestDTO,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("mainImageUrl") String mainImageUrl) throws IOException {

        Long postId = postService.createPost(postRequestDTO, files, mainImageUrl);
        Map<String, Object> response = new HashMap<>();
        response.put("postId", postId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/detail")
    public String postDetail(@RequestParam Long postId, Model model) {
        PostResponseDTO post = postService.findPostById(postId);
        model.addAttribute("post", post);
        return "pages/post/postDetail";
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
