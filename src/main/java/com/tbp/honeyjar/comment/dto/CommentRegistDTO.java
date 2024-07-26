package com.tbp.honeyjar.comment.dto;

public class CommentRegistDTO {
    private String comment;
    private Long userId;
    private Long postId;

    public CommentRegistDTO() {
    }

    public CommentRegistDTO(String comment, Long userId, Long postId) {
        this.comment = comment;
        this.userId = userId;
        this.postId = postId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    @Override
    public String toString() {
        return "CommentRegistDTO{" +
                "comment='" + comment + '\'' +
                ", userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
