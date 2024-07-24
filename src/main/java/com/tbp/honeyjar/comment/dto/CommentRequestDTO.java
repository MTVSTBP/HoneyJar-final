package com.tbp.honeyjar.comment.dto;

import lombok.Data;


public class CommentRequestDTO {
    private Long commentId;
    private String content;

    public CommentRequestDTO() {
    }

    public CommentRequestDTO(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentRequestDTO{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                '}';
    }
}
