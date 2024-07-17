package com.tbp.honeyjar.bookmark.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkDTO {
    private Long postId;
    private boolean bookmarked;

    @Builder
    public BookmarkDTO(Long postId, boolean bookmarked) {
        this.postId = postId;
        this.bookmarked = bookmarked;
    }
}
