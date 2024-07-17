package com.tbp.honeyjar.post.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsDTO {
    private Long id = 1L;
    private String img1 = "/assets/svg/img_01.JPG";
    private String postTitle = "title";
    private String nickname = "nickname";
    private String category;
    private Integer recommendation;
    private Double rating;
    private Double distance;
    private boolean goodRestaurant;

    @Builder
    public PostsDTO(Long id, String img1, String postTitle, String nickname, String category, Integer recommendation, Double rating, Double distance, boolean goodRestaurant) {
        this.id = id;
        this.img1 = img1;
        this.postTitle = postTitle;
        this.nickname = nickname;
        this.category = category;
        this.recommendation = recommendation;
        this.rating = rating;
        this.distance = distance;
        this.goodRestaurant = goodRestaurant;
    }

}