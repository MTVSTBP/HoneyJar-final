package com.tbp.honeyjar.post.dto;

import java.time.LocalDateTime;
import java.util.List;


public class PostRequestDTO {
    private Long postId;
    private String title;
    private String recommendMenu;
    private int price;
    private String post;
    private LocalDateTime createAt;
//    private String foodCategory;
    private String placeName;
//    private String xCoordinate;
//    private String yCoordinate;
    private List<String> imageUrls;  // 이미지 URL 리스트
    private String mainImageUrl;  // 썸네일 이미지 URL
    private Long userId;  // 추가: 사용자 ID

    public PostRequestDTO() {}

    public PostRequestDTO(Long postId, String title, String recommendMenu, int price, String post, LocalDateTime createAt, String placeName, List<String> imageUrls, String mainImageUrl, Long userId) {
        this.postId = postId;
        this.title = title;
        this.recommendMenu = recommendMenu;
        this.price = price;
        this.post = post;
        this.createAt = createAt;
        this.placeName = placeName;
        this.imageUrls = imageUrls;
        this.mainImageUrl = mainImageUrl;
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecommendMenu() {
        return recommendMenu;
    }

    public void setRecommendMenu(String recommendMenu) {
        this.recommendMenu = recommendMenu;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PostRequestDTO{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", recommendMenu='" + recommendMenu + '\'' +
                ", price=" + price +
                ", post='" + post + '\'' +
                ", createAt=" + createAt +
                ", placeName='" + placeName + '\'' +
                ", imageUrls=" + imageUrls +
                ", mainImageUrl='" + mainImageUrl + '\'' +
                ", userId=" + userId +
                '}';
    }
}