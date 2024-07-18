package com.tbp.honeyjar.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDTO {
    private Long postId;
    private String title;
    private String recommendMenu;
    private int price;
    private String post;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String foodCategory;
    private String placeName;
    private String xCoordinate;
    private String yCoordinate;
    private String profileImg;
    private int like;
    private long commentId;
    private int rating;
}
