package com.tbp.honeyjar.post.dto;

import java.time.LocalDateTime;
import java.util.List;


public class PostResponseDTO {
    private Long postId;
    private Long categoryId;
    private String categoryName;
    private String title;
    private String recommendMenu;
    private int price;
    private String post;
    private Long placeId;
    private String placeName; // place 이름
    private String xCoordinate; // x좌표
    private String yCoordinate; // y좌표
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> imageUrls;  // 이미지 URL 리스트
    private String mainImageUrl;  // 썸네일 이미지 URL

    //    private String profileImg;  // user table
//    private int like;  // like table
//    private long commentId;  // comment table
//    private int rating;  // rating table

    public PostResponseDTO() {}


    public PostResponseDTO(Long postId, Long categoryId, String categoryName, String title, String recommendMenu, int price, String post, Long placeId, String placeName, String xCoordinate, String yCoordinate, LocalDateTime createdAt, LocalDateTime updatedAt, List<String> imageUrls, String mainImageUrl) {
        this.postId = postId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.title = title;
        this.recommendMenu = recommendMenu;
        this.price = price;
        this.post = post;
        this.placeId = placeId;
        this.placeName = placeName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageUrls = imageUrls;
        this.mainImageUrl = mainImageUrl;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(String xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public String getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(String yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    @Override
    public String toString() {
        return "PostResponseDTO{" +
                "postId=" + postId +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", title='" + title + '\'' +
                ", recommendMenu='" + recommendMenu + '\'' +
                ", price=" + price +
                ", post='" + post + '\'' +
                ", placeId=" + placeId +
                ", placeName='" + placeName + '\'' +
                ", xCoordinate='" + xCoordinate + '\'' +
                ", yCoordinate='" + yCoordinate + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", imageUrls=" + imageUrls +
                ", mainImageUrl='" + mainImageUrl + '\'' +
                '}';
    }
}

