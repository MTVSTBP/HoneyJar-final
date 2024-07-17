package com.tbp.honeyjar.post.DTO;

import com.tbp.honeyjar.bookmark.DTO.BookmarkDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostWithBookmarkDTO {

    private PostsDTO post;
    private BookmarkDTO bookmark;

    @Builder
    public PostWithBookmarkDTO(PostsDTO post, BookmarkDTO bookmark) {
        this.post = post;
        this.bookmark = bookmark;
    }
}
