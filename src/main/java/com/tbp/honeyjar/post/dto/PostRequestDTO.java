package com.tbp.honeyjar.post.dto;

import com.tbp.honeyjar.image.dto.ImageDTO;

import java.util.List;


public class PostRequestDTO {
    private Long postId;
    private String title;
    private String recommendMenu;
    private int price;
    private String post;
    private Long placeId;
    private Long userId;
    private List<String> imageUrls;
    private String mainImageUrl;

    public PostRequestDTO() {}

    public PostRequestDTO(Long postId, String title, String recommendMenu, int price, String post, Long placeId, Long userId, List<String> imageUrls, String mainImageUrl) {
        this.postId = postId;
        this.title = title;
        this.recommendMenu = recommendMenu;
        this.price = price;
        this.post = post;
        this.placeId = placeId;
        this.userId = userId;
        this.imageUrls = imageUrls;
        this.mainImageUrl = mainImageUrl;
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

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
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

    @Override
    public String toString() {
        return "PostRequestDTO{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", recommendMenu='" + recommendMenu + '\'' +
                ", price=" + price +
                ", post='" + post + '\'' +
                ", placeId=" + placeId +
                ", userId=" + userId +
                ", imageUrls=" + imageUrls +
                ", mainImageUrl='" + mainImageUrl + '\'' +
                '}';
    }
}