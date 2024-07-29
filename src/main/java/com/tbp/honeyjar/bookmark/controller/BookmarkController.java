package com.tbp.honeyjar.bookmark.controller;

import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.bookmark.dto.BookmarkDTO;
import com.tbp.honeyjar.bookmark.service.BookmarkService;
import com.tbp.honeyjar.login.service.user.UserService;
import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.service.MyPageService;
import com.tbp.honeyjar.post.dto.PostListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/bookmarks")
public class BookmarkController {

    private static final Logger log = LoggerFactory.getLogger(BookmarkController.class);
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final CategoryService categoryService;

    public BookmarkController(BookmarkService bookmarkService, UserService userService, CategoryService categoryService) {
        this.bookmarkService = bookmarkService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping("/toggle")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> toggleBookmark(@RequestParam Long postId, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        boolean isBookmarked = bookmarkService.toggleBookmark(postId, userId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("bookmarked", isBookmarked);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public String getBookmarks(Model model, @RequestParam(required = false) Long category, Principal principal) {
        Long userId = userService.findUserIdByKakaoId(principal.getName());
        List<PostListDTO> bookmarkedPosts = bookmarkService.getBookmarkedPosts(userId, category);

        model.addAttribute("posts", bookmarkedPosts);
        model.addAttribute("categories", categoryService.findAllFoodCategory());
        model.addAttribute("selectedCategory", category);
        return "pages/bookmark/bookmark";
    }
}
