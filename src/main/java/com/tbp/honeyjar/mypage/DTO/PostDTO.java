package com.tbp.honeyjar.mypage.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDTO {
    private Long postId;
    private Long userId;
    private String postTitle;
    private String postWriter;
    private String postWriterProfileImage;
    private String postThumbnail;
    private boolean isBookmarked;

    @Builder
    public PostDTO(Long postId, String postTitle, String postWriter, String postWriterProfileImage, String postThumbnail, boolean isBookmarked) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postWriter = postWriter;
        this.postWriterProfileImage = postWriterProfileImage;
        this.postThumbnail = postThumbnail;
        this.isBookmarked = isBookmarked;
    }

    public Long getPostId() {
        return postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostWriter() {
        return postWriter;
    }

    public String getPostWriterProfileImage() {
        return postWriterProfileImage;
    }

    public String getPostThumbnail() {
        return postThumbnail;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", postTitle='" + postTitle + '\'' +
                ", postWriter='" + postWriter + '\'' +
                ", postWriterProfileImage='" + postWriterProfileImage + '\'' +
                ", postThumbnail='" + postThumbnail + '\'' +
                ", isBookmarked=" + isBookmarked +
                '}';
    }
}