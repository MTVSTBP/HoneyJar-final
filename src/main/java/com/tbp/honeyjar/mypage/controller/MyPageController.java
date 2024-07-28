package com.tbp.honeyjar.mypage.controller;
import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.login.service.user.UserService;
import com.tbp.honeyjar.mypage.DTO.CategoryDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageCorrectionDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.DTO.PostDTO;
import com.tbp.honeyjar.mypage.service.MyPageService;
import com.tbp.honeyjar.post.dao.PostMapper;
import com.tbp.honeyjar.post.dto.PostListDTO;
import com.tbp.honeyjar.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    private final MyPageService myPageService;
    private final UserService userService;
    private final PostService postService;
    private final CategoryService categoryService;

    @Autowired
    public MyPageController(MyPageService myPageService, UserService userService, PostService postService, CategoryService categoryService) {
        this.myPageService = myPageService;
        this.userService = userService;
        this.postService = postService;
        this.categoryService = categoryService;
    }
    @GetMapping
    public String getMyPage(Model model, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        System.out.println(userId);
        MyPageDTO myPage = myPageService.getMyPage(userId);
        List<PostListDTO> postList =myPageService.getMyPost(userId);
        model.addAttribute("myPage", myPage);
        model.addAttribute("posts", postList);
        return "pages/mypage/myPage";
    }
    @PostMapping("/correction")
    @ResponseBody
    public ResponseEntity<MyPageCorrectionDTO> updateMyPageCorrection(@RequestBody MyPageCorrectionDTO myPageCorrectionDTO, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        myPageCorrectionDTO.setUserId(userId);
        myPageService.updateMyProfile(myPageCorrectionDTO);

        return new ResponseEntity<>(myPageCorrectionDTO, HttpStatus.OK);
    }
    @GetMapping("/correction")
    public String getMyPageCorrection(Model model, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        MyPageCorrectionDTO myPage = myPageService.getMyProfile(userId);
        model.addAttribute("myPage", myPage);
        return "pages/mypage/myPageCorrection";
    }
    @GetMapping(value="category", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CategoryDTO> getMyPageCategory() {return myPageService.getCategoryList();}
    @GetMapping("/bookmark")
    public String getMyPageBookmark(Model model, Principal principal, @RequestParam(required = false) Long category) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        List<PostListDTO> posts = myPageService.getMyBookmark(category, userId);
        model.addAttribute("posts", posts);
        model.addAttribute("categories", categoryService.findAllFoodCategory());
        model.addAttribute("selectedCategory", category);
        return "pages/mypage/bookmark";
    }
    @GetMapping("/mypost")
    public String getMyPost(Model model, Principal principal, @RequestParam(required = false) Long category) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        List<PostListDTO> posts = myPageService.getMyPosts(category, userId);
        model.addAttribute("posts", posts);
        model.addAttribute("categories", categoryService.findAllFoodCategory());
        model.addAttribute("selectedCategory", category);
        return "pages/mypage/myPost";
    }
}