package com.tbp.honeyjar.post.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDTO {
    private Long id;
    private String img1;
    private List<String> images;
    private String postTitle;
    private String nickname;
    private String category;
    private String bestMenu;
    private Integer price;
    private String address;
    private String content;
    private boolean publicPost;
    private String date;
    private int likeCount; // 추가
    private double averageRating; // 추가

    @Builder
    public PostResponseDTO(Long id, String img1, List<String> images, String postTitle, String nickname, String category, String bestMenu, Integer price, String address, String content, boolean publicPost, String date, int likeCount, double averageRating) {
        this.id = id;
        this.img1 = img1;
        this.images = images;
        this.postTitle = postTitle;
        this.nickname = nickname;
        this.category = category;
        this.bestMenu = bestMenu;
        this.price = price;
        this.address = address;
        this.content = content;
        this.publicPost = publicPost;
        this.date = date;
        this.likeCount = likeCount; // 추가
        this.averageRating = averageRating; // 추가
    }
}
