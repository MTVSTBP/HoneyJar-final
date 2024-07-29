package com.tbp.honeyjar.image.dto;



public class ImageDTO {
    private Long imageId;
    private Long userId;
    private String url;
    private Long postId;
    private boolean isMain;

    public ImageDTO() {}

    public ImageDTO(Long imageId, Long userId, String url, Long postId, boolean isMain) {
        this.imageId = imageId;
        this.userId = userId;
        this.url = url;
        this.postId = postId;
        this.isMain = isMain;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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