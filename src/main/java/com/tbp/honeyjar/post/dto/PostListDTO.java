package com.tbp.honeyjar.post.dto;


public class PostListDTO {
    private Long postId;
    private String postTitle;
    private String post;
    private String profileImg;

    public PostListDTO() {}

    public PostListDTO(Long postId, String postTitle, String post, String profileImg) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.post = post;
        this.profileImg = profileImg;
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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    @Override
    public String toString() {
        return "PostListDTO{" +
                "postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", post='" + post + '\'' +
                ", profileImg='" + profileImg + '\'' +
                '}';
    }
}