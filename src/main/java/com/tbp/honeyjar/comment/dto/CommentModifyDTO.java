package com.tbp.honeyjar.comment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CommentModifyDTO {
    private Long commentId;
    private String comment;
    private Long postId;

    public CommentModifyDTO() {
    }

    public CommentModifyDTO(Long commentId, String comment, Long postId) {
        this.commentId = commentId;
        this.comment = comment;
        this.postId = postId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getPostId() { return postId;}

    public void setPostId(Long postId) { this.postId = postId;}


    @Override
    public String toString() {
        return "CommentModifyDTO{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", postId=" + postId +
                '}';
    }
}