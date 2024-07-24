package com.tbp.honeyjar.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommentDeleteDTO {
    private long commentId;
    private long postId;

    public CommentDeleteDTO() {
    }

    public CommentDeleteDTO(long commentId, long postId) {
        this.commentId = commentId;
        this.postId = postId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "CommentDeleteDTO{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                '}';
    }
}
