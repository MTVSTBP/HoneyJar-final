package com.tbp.honeyjar.comment.dto;

import lombok.Data;

@Data
public class CommentRegistDTO {
    private String comment;
    private long userId;
    private long postId;
}
