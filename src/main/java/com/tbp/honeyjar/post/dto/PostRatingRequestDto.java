package com.tbp.honeyjar.post.dto;

import lombok.Data;

@Data
public class PostRatingRequestDto {

    private Long postId;
    private Long userId;
    private float rating;
}
