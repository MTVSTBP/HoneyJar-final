package com.tbp.honeyjar.comment.dto;


public class CommentRequestDTO {
    private Long commentId;
    private String content;
    private Long userId;

    public CommentRequestDTO() {
    }

    public CommentRequestDTO(Long commentId, String content, Long userId) {
        this.commentId = commentId;
        this.content = content;
        this.userId = userId;
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

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "CommentRequestDTO{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                '}';
    }
}
