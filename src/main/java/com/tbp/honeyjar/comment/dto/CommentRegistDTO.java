package com.tbp.honeyjar.comment.dto;

import lombok.Data;


public class CommentRegistDTO {
    private String comment;
    private long userId;
    private long postId;

    public CommentRegistDTO() {
    }

    public CommentRegistDTO(String comment, long userId, long postId) {
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
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
