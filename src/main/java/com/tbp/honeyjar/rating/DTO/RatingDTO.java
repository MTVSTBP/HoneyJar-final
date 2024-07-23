package com.tbp.honeyjar.rating.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RatingDTO {
    private Long postId;
    private double averageRating;

    @Builder
    public RatingDTO(Long postId, double averageRating) {
        this.postId = postId;
        this.averageRating = averageRating;
    }
}
