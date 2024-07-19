package com.tbp.honeyjar.post.dto;

import java.time.LocalDateTime;
import java.util.List;


public class PostResponseDTO {
    private Long postId;
    private String title;
    private String recommendMenu;
    private int price;
    private String post;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String foodCategory;
    private String placeName; // place 이름
    private String xCoordinate; // x좌표
    private String yCoordinate; // y좌표
    private String profileImg;  // user table
    private int like;  // like table
    private long commentId;  // comment table
    private int rating;  // rating table
    private List<String> imageUrls;  // 이미지 URL 리스트
    private String mainImageUrl;  // 썸네일 이미지 URL

    public PostResponseDTO() {}

    public PostResponseDTO(Long postId, String title, String recommendMenu, int price, String post, LocalDateTime createdAt, LocalDateTime updatedAt, String foodCategory, String placeName, String xCoordinate, String yCoordinate, String profileImg, int like, long commentId, int rating, List<String> imageUrls, String mainImageUrl) {
        this.postId = postId;
        this.title = title;
        this.recommendMenu = recommendMenu;
        this.price = price;
        this.post = post;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.foodCategory = foodCategory;
        this.placeName = placeName;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.profileImg = profileImg;
        this.like = like;
        this.commentId = commentId;
        this.rating = rating;
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

    public LocalDateTime getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createdAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updatedAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updatedAt = updateAt;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
                ", title='" + title + '\'' +
                ", recommendMenu='" + recommendMenu + '\'' +
                ", price=" + price +
                ", post='" + post + '\'' +
                ", createAt=" + createdAt +
                ", updateAt=" + updatedAt +
                ", foodCategory='" + foodCategory + '\'' +
                ", placeName='" + placeName + '\'' +
                ", xCoordinate='" + xCoordinate + '\'' +
                ", yCoordinate='" + yCoordinate + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", like=" + like +
                ", commentId=" + commentId +
                ", rating=" + rating +
                ", imageUrls=" + imageUrls +
                ", mainImageUrl='" + mainImageUrl + '\'' +
                '}';
    }
}

