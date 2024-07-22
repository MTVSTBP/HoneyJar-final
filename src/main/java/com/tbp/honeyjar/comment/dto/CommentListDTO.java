package com.tbp.honeyjar.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentListDTO {
    private long userId;
    private long postId;
    private Long commentId;
    private String comment;
    private String updatedAt;
    private String userName;
    private String url;

    @Builder
    public CommentListDTO(long userId, long postId, Long commentId, String comment, String updatedAt, String userName, String url) {
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.comment = comment;
        this.updatedAt = updatedAt;
        this.userName = userName;
        this.url = url;
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
                ", url='" + url + '\'' +
                '}';
    }
}
