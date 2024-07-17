package com.tbp.honeyjar.post.controller;

import com.tbp.honeyjar.bookmark.DTO.BookmarkDTO;
import com.tbp.honeyjar.like.DTO.LikeDTO;
import com.tbp.honeyjar.post.DTO.*;
import com.tbp.honeyjar.rating.DTO.RatingDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/post")
public class PostController {

    @GetMapping
    public String postList(@RequestParam(name = "category", required = false) String category,
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

        return "pages/post/post";
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

    private List<CategoryDTO> getCategories() {
        List<CategoryDTO> categories = new ArrayList<>();
        categories.add(CategoryDTO.builder().id("category1").name("일식").build());
        categories.add(CategoryDTO.builder().id("category2").name("한식").build());
        categories.add(CategoryDTO.builder().id("category3").name("중식").build());
        categories.add(CategoryDTO.builder().id("category4").name("양식").build());
        return categories;
    }

    private List<SortOptionDTO> getSortOptions() {
        List<SortOptionDTO> sortOptions = new ArrayList<>();
        sortOptions.add(SortOptionDTO.builder().id("recommendation").name("추천순").build());
        sortOptions.add(SortOptionDTO.builder().id("rating").name("별점순").build());
        sortOptions.add(SortOptionDTO.builder().id("distance").name("거리순").build());
        return sortOptions;
    }

    private List<BookmarkDTO> getBookmarks() {
        List<BookmarkDTO> bookmarks = new ArrayList<>();
        bookmarks.add(BookmarkDTO.builder().postId(1L).bookmarked(true).build());
        bookmarks.add(BookmarkDTO.builder().postId(2L).bookmarked(false).build());
        return bookmarks;
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


    @GetMapping("/write")
    public String selectImageList(Model model) {
        // 초기 데이터 설정
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        model.addAttribute("postRequestDTO", postRequestDTO);

        // 카테고리 목록 설정
        List<CategoryDTO> categories = getCategories();
        model.addAttribute("categories", categories);

        return "pages/post/postWrite";
    }

    @GetMapping("/detail")
    public String detail(Model model) {
        // 테스트용 데이터 생성
        PostResponseDTO postResponseDTO = PostResponseDTO.builder()
                .id(1L)
                .img1("/assets/svg/img_01.JPG")
                .images(Arrays.asList("/assets/svg/img_01.JPG", "/assets/svg/img_02.JPG"))
                .postTitle("맛있다는 제목")
                .nickname("작성자닉네임")
                .category("일식")
                .bestMenu("교토식 오코노미야끼, 츄라이")
                .price(30000)
                .address("경기도 성남시 수정구 대왕판교로 815")
                .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Corrupisti temperantia porta deserunt solida facilius verear mediocris ignoratione eveniet. Eosdem artes exhorrescere moribus imperitorum. Quales vocant familias, disciplinam tamquam proficiscuntur timidiores, quondam pellentesque peccant, audiam. Scribendi licet omnino, caeco filium teneam curis amicitia. Ita dialectica certae sophocles tantalo invidi atomorum solida. Rationem quoniam o miser inhaererent dixi torqueantur animum. Sale tortor officiis videatur beateque liberabuntur posse accusantium terentii coniunctione summum. Censes percipi probabis medium ante comprobavit, iusto diogenem tempora momenti aequitatem. Multam orci, sciscat odia perpessio nocet asperum.")
                .publicPost(true)
                .date(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .likeCount(10) // 임시 데이터
                .averageRating(4.5) // 임시 데이터
                .build();

        model.addAttribute("postDetail", postResponseDTO);
        return "pages/post/postDetail";
    }

    @GetMapping("/correction")
    public String postCorrection(Model model) {
        PostRequestDTO postRequestDTO = PostRequestDTO.builder()
                .category("category1")
                .postTitle("맛있다는 제목")
                .bestMenu("교토식 오코노미야끼")
                .price(30000)
                .address("경기도 성남시 수정구 대왕판교로 815")
                .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Corrupisti temperantia porta deserunt solida facilius verear mediocris ignoratione eveniet. Eosdem artes exhorrescere moribus imperitorum. Quales vocant familias, disciplinam tamquam proficiscuntur timidiores, quondam pellentesque peccant, audiam. Scribendi licet omnino, caeco filium teneam curis amicitia. Ita dialectica certae sophocles tantalo invidi atomorum solida. Rationem quoniam o miser inhaererent dixi torqueantur animum. Sale tortor officiis videatur beateque liberabuntur posse accusantium terentii coniunctione summum. Censes percipi probabis medium ante comprobavit, iusto diogenem tempora momenti aequitatem. Multam orci, sciscat odia perpessio nocet asperum.")
                .img1("/assets/svg/img_01.JPG")
                .publicPost(true) // 공개 여부 설정
                .build();

        model.addAttribute("postRequestDTO", postRequestDTO);

        List<CategoryDTO> categories = getCategories();
        model.addAttribute("categories", categories);

        return "pages/post/postCorrection";
    }



    @GetMapping("/map")
    public String findAddress() {
        return "pages/post/findAddress";
    }

}
