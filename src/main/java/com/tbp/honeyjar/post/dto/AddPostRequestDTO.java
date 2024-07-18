package com.tbp.honeyjar.post.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class AddPostRequestDTO {
    private Long postId;
    private String title;
    private String recommendMenu;
    private int price;
    private String post;
    private LocalDateTime updateAt;
    private String foodCategory;
    private String placeName;
    private String xCoordinate;
    private String yCoordinate;
}