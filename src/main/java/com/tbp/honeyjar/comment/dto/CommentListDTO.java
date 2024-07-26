package com.tbp.honeyjar.comment.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Data
public class CommentListDTO {
    private long userId;
    private long postId;
    private long commentId;
    private String comment;
    private LocalDateTime createdAt;
    private String userName;

    @Getter
    private String formattedCreatedAt;

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        if (createdAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 HH시 mm분");
            this.formattedCreatedAt = createdAt.format(formatter);
        }
    }

}
