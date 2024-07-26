package com.tbp.honeyjar.post.dto;


public class PostListDTO {
    private Long postId;
    private String postTitle;
    private String post;
    private String mainImageUrl;
    private Boolean bookmarked;
    private Long categoryId;
    private String userName;
    private Long placeId;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double distance;
//    private String profileImg;

    public PostListDTO() {}

    public PostListDTO(Long postId, String postTitle, String post, String mainImageUrl, Boolean bookmarked, Long categoryId, String userName, Long placeId, Double xCoordinate, Double yCoordinate, Double distance) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.post = post;
        this.mainImageUrl = mainImageUrl;
        this.bookmarked = bookmarked;
        this.categoryId = categoryId;
        this.userName = userName;
        this.placeId = placeId;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.distance = distance;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public Boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public Double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "PostListDTO{" +
                "postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", post='" + post + '\'' +
                ", mainImageUrl='" + mainImageUrl + '\'' +
                ", bookmarked=" + bookmarked +
                ", categoryId=" + categoryId +
                ", userName='" + userName + '\'' +
                ", placeId=" + placeId +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", distance=" + distance +
                '}';
    }
}