package com.tbp.honeyjar.bookmark.controller;

import com.tbp.honeyjar.bookmark.DTO.BookmarkDTO;
import com.tbp.honeyjar.post.DTO.CategoryDTO;
import com.tbp.honeyjar.post.DTO.PostWithBookmarkDTO;
import com.tbp.honeyjar.post.DTO.PostsDTO;
import com.tbp.honeyjar.post.DTO.SortOptionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController {

    @GetMapping
    public String getBookmarkedPosts(@RequestParam(name = "category", required = false) String category,
                                     @RequestParam(name = "sortOption", required = false) String sortOption,
                                     @RequestParam(name = "goodRestaurant", required = false) Boolean goodRestaurant,
                                     Model model) {
        List<PostsDTO> posts = getAllPosts();
        List<BookmarkDTO> bookmarks = getBookmarks();
        List<PostWithBookmarkDTO> postWithBookmarks = mergePostsAndBookmarks(posts, bookmarks);

        // 필터링 로직 추가
        if (category != null && !category.isEmpty()) {
            postWithBookmarks = postWithBookmarks.stream()
                    .filter(postWithBookmark -> postWithBookmark.getPost().getCategory().equals(category))
                    .collect(Collectors.toList());
        }

        // 정렬 로직 추가
        if (sortOption != null && !sortOption.isEmpty()) {
            postWithBookmarks.sort(getComparator(sortOption));
        }

        // 체크박스 상태 및 선택된 옵션 유지
        model.addAttribute("selectedCategory", category != null ? category : "");
        model.addAttribute("selectedSortOption", sortOption != null ? sortOption : "");
        model.addAttribute("goodRestaurant", goodRestaurant != null ? goodRestaurant : false);
        model.addAttribute("postWithBookmarks", postWithBookmarks);

        List<CategoryDTO> categories = getCategories();
        model.addAttribute("categories", categories);

        List<SortOptionDTO> sortOptions = getSortOptions();
        model.addAttribute("sortOptions", sortOptions);

        return "pages/bookmark/bookmarkedPosts";
    }

    private List<PostWithBookmarkDTO> mergePostsAndBookmarks(List<PostsDTO> posts, List<BookmarkDTO> bookmarks) {
        Map<Long, BookmarkDTO> bookmarkMap = bookmarks.stream()
                .collect(Collectors.toMap(BookmarkDTO::getPostId, Function.identity()));

        return posts.stream()
                .map(post -> PostWithBookmarkDTO.builder()
                        .post(post)
                        .bookmark(bookmarkMap.getOrDefault(post.getId(), BookmarkDTO.builder().postId(post.getId()).bookmarked(false).build()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<PostsDTO> getAllPosts() {
        List<PostsDTO> posts = new ArrayList<>();
        posts.add(PostsDTO.builder().id(1L).img1("/assets/svg/img_01.JPG").postTitle("Post 1").nickname("User1").category("category1").build());
        posts.add(PostsDTO.builder().id(2L).img1("/assets/svg/img_02.JPG").postTitle("Post 2").nickname("User2").category("category4").build());
        return posts;
    }

    private List<BookmarkDTO> getBookmarks() {
        List<BookmarkDTO> bookmarks = new ArrayList<>();
        bookmarks.add(BookmarkDTO.builder().postId(1L).bookmarked(true).build());
        bookmarks.add(BookmarkDTO.builder().postId(2L).bookmarked(false).build());
        return bookmarks;
    }

    private List<CategoryDTO> getCategories() {
        List<CategoryDTO> categories = new ArrayList<>();
        categories.add(CategoryDTO.builder().id("category1").name("일식").build());
        categories.add(CategoryDTO.builder().id("category2").name("한식").build());
        categories.add(CategoryDTO.builder().id("category3").name("중식").build());
        categories.add(CategoryDTO.builder().id("category4").name("양식").build());
        // 필요한 카테고리들을 추가하세요

        return categories;
    }

    private List<SortOptionDTO> getSortOptions() {
        List<SortOptionDTO> sortOptions = new ArrayList<>();
        sortOptions.add(SortOptionDTO.builder().id("recommendation").name("추천순").build());
        sortOptions.add(SortOptionDTO.builder().id("rating").name("별점순").build());
        sortOptions.add(SortOptionDTO.builder().id("distance").name("거리순").build());
        // 필요한 정렬 옵션들을 추가하세요

        return sortOptions;
    }

    private Comparator<PostWithBookmarkDTO> getComparator(String sortOption) {
        switch (sortOption) {
            case "recommendation":
                return Comparator.comparing(postWithBookmarkDTO -> postWithBookmarkDTO.getPost().getRecommendation());
            case "rating":
                return Comparator.comparing(postWithBookmarkDTO -> postWithBookmarkDTO.getPost().getRating());
            case "distance":
                return Comparator.comparing(postWithBookmarkDTO -> postWithBookmarkDTO.getPost().getDistance());
            default:
                return Comparator.comparing(postWithBookmarkDTO -> postWithBookmarkDTO.getPost().getId());
        }
    }

    @PostMapping("/toggle")
    public String toggleBookmark(@RequestParam Long postId) {
        // 북마크 상태를 토글하는 로직을 구현하세요
        // 예시: bookmarkService.toggleBookmark(postId);
        return "redirect:/bookmarks";
    }
}