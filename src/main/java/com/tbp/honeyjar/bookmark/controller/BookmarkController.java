package com.tbp.honeyjar.bookmark.controller;

import com.tbp.honeyjar.bookmark.dto.BookmarkDTO;
import com.tbp.honeyjar.bookmark.service.BookmarkService;
import com.tbp.honeyjar.login.service.user.UserService;
import com.tbp.honeyjar.post.dto.PostListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@Controller
//@RequestMapping("/bookmarks")
//public class BookmarkController {
//
//    private final BookmarkService bookmarkService;
//    private final UserService userService;
//
//    public BookmarkController(BookmarkService bookmarkService, UserService userService) {
//        this.bookmarkService = bookmarkService;
//        this.userService = userService;
//    }
//
//    @PostMapping("/toggle")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> toggleBookmark(@RequestParam Long postId, Principal principal) {
//        Long userId = userService.findUserIdByKakaoId(principal.getName());
//        bookmarkService.toggleBookmark(postId, userId);
//        boolean isBookmarked = bookmarkService.isBookmarked(postId, userId);
//        Map<String, Object> response = new HashMap<>();
//        response.put("success", true);
//        response.put("bookmarked", isBookmarked);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/all")
//    public String getAllBookmarks(Principal principal, Model model) {
//        Long userId = userService.findUserIdByKakaoId(principal.getName());
//        List<PostListDTO> posts = bookmarkService.getAllPostWithBookmarksByUserId(userId);
//        model.addAttribute("posts", posts);
//        return "pages/bookmark/bookmark";
//    }
//}
