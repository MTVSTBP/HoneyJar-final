package com.tbp.honeyjar.post.dto;

import lombok.Data;

@Data
public class PostLikeRequestDto {

    private Long postId;
    private Long userId;
}
