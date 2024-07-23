package com.tbp.honeyjar.post.dto;

import com.tbp.honeyjar.image.dto.ImageDTO;
import com.tbp.honeyjar.place.dto.PlaceDTO;

import java.time.LocalDateTime;
import java.util.List;


public class PostRequestDTO {
    private Long postId;
    private String title;
    private String recommendMenu;
    private int price;
    private String post;
    private Long userId;
    private List<String> imageUrls;
    private String mainImageUrl;
    private PlaceDTO place;
    private Long placeId;
    private LocalDateTime createdAt;
    private Long categoryId;

    public PostRequestDTO() {}

    public PostRequestDTO(Long postId, String title, String recommendMenu, int price, String post, Long userId, List<String> imageUrls, String mainImageUrl, PlaceDTO place, Long placeId, LocalDateTime createdAt, Long categoryId) {
        this.postId = postId;
        this.title = title;
        this.recommendMenu = recommendMenu;
        this.price = price;
        this.post = post;
        this.userId = userId;
        this.imageUrls = imageUrls;
        this.mainImageUrl = mainImageUrl;
        this.place = place;
        this.placeId = placeId;
        this.createdAt = createdAt;
        this.categoryId = categoryId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public PlaceDTO getPlace() {
        return place;
    }

    public void setPlace(PlaceDTO place) {
        this.place = place;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "PostRequestDTO{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", recommendMenu='" + recommendMenu + '\'' +
                ", price=" + price +
                ", post='" + post + '\'' +
                ", userId=" + userId +
                ", imageUrls=" + imageUrls +
                ", mainImageUrl='" + mainImageUrl + '\'' +
                ", place=" + place +
                ", placeId=" + placeId +
                ", createdAt=" + createdAt +
                ", categoryId=" + categoryId +
                '}';
    }
}