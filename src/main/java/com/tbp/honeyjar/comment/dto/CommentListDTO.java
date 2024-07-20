package com.tbp.honeyjar.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentListDTO {
    private Long commentId; //
    private String comment; //
    private String createdAt; //
    private String updatedAt;
    private Long postId;
    private String userId; //
    private String profileImg; // DB 컬럼 추가후 수정예정

    @Builder
    public CommentListDTO(Long commentId, String comment, String createdAt, String updatedAt, Long postId, String userId, String profileImg) {
        this.commentId = commentId;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.postId = postId;
        this.userId = userId;
        this.profileImg = "assets/svg/base_profile.svg";
    }

    @Override
    public String toString() {
        return "CommentListDTO{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", postId=" + postId +
                ", userId='" + userId + '\'' +
                ", profileImg='" + profileImg + '\'' +
                '}';
    }
}
