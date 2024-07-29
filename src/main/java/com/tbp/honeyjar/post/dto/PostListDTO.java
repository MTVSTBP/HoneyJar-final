package com.tbp.honeyjar.post.dto;


public class PostListDTO {
    private Long postId;
    private String postTitle;
    private String post;
    private String mainImageUrl;
    private Boolean bookmarked;
//    private String profileImg;

    public PostListDTO() {}

    public PostListDTO(Long postId, String postTitle, String post, String mainImageUrl, Boolean bookmarked) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.post = post;
        this.mainImageUrl = mainImageUrl;
        this.bookmarked = bookmarked;
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

    @Override
    public String toString() {
        return "PostListDTO{" +
                "postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", post='" + post + '\'' +
                ", mainImageUrl='" + mainImageUrl + '\'' +
                ", bookmarked=" + bookmarked +
                '}';
    }
}