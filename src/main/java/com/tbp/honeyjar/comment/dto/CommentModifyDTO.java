package com.tbp.honeyjar.comment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CommentModifyDTO {
    private Long commentId;
    private String comment;

    public CommentModifyDTO() {
    }

    public CommentModifyDTO(Long commentId, String comment) {
        this.commentId = commentId;
        this.comment = comment;
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

    @Override
    public String toString() {
        return "CommentModifyDTO{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                '}';
    }
}