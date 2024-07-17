package com.tbp.honeyjar.like.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeDTO {
    private Long postId;
    private int likeCount;

    @Builder
    public LikeDTO(Long postId, int likeCount) {
        this.postId = postId;
        this.likeCount = likeCount;
    }
}
