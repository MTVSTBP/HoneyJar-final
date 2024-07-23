package com.tbp.honeyjar.comment.dto;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private Long commentId;
    private String content;
}
