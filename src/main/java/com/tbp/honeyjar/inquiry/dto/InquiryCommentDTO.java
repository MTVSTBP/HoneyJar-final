package com.tbp.honeyjar.inquiry.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InquiryCommentDTO {
    private Long commentId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long inquiryId;
    private Long userId;
}
