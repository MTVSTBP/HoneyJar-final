package com.tbp.honeyjar.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CommentListDTO {
    private long userId;
    private long postId;
    private long commentId;
    private String comment;
    private String updatedAt;
    private String userName;

    public CommentListDTO() {
    }

    public CommentListDTO(long userId, long postId, long commentId, String comment, String updatedAt, String userName) {
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.comment = comment;
        this.updatedAt = updatedAt;
        this.userName = userName;
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

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "CommentListDTO{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
