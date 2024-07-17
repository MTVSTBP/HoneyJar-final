package com.tbp.honeyjar.post.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDTO {
    private Long id;
    private String img1;
    private String postTitle;
    private String nickname;
    private String category;
    private String bestMenu;
    private Integer price;
    private String address;
    private String content;
    private boolean publicPost = true; // 기본값 설정

    @Builder
    public PostRequestDTO(Long id, String img1, String postTitle, String nickname, String category, String bestMenu, Integer price, String address, String content, boolean publicPost) {
        this.id = id;
        this.img1 = img1;
        this.postTitle = postTitle;
        this.nickname = nickname;
        this.category = category;
        this.bestMenu = bestMenu;
        this.price = price;
        this.address = address;
        this.content = content;
        this.publicPost = publicPost;
    }
}