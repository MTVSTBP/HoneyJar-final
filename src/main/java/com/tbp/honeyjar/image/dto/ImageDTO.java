package com.tbp.honeyjar.image.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ImageDTO {
    private Long imageId;
    private String url;
    private Long userId;
    private Long postId;
    private boolean isMain;

    public ImageDTO() {}

    public ImageDTO(Long imageId, String url, Long userId, Long postId, boolean isMain) {
        this.imageId = imageId;
        this.url = url;
        this.userId = userId;
        this.postId = postId;
        this.isMain = isMain;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    @Override
    public String toString() {
        return "ImageDTO{" +
                "imageId=" + imageId +
                ", url='" + url + '\'' +
                ", userId=" + userId +
                ", postId=" + postId +
                ", isMain=" + isMain +
                '}';
    }
}