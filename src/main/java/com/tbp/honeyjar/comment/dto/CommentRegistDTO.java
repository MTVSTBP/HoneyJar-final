package com.tbp.honeyjar.comment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CommentRegistDTO {
    private String comment;
    private Long userId;
    private Long postId;


    public CommentRegistDTO(String comment, long userId, long postId) {
        this.comment = comment;
        this.userId = userId;
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
