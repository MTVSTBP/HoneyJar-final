package com.tbp.honeyjar.inquiry.dto;

import lombok.Data;

@Data
public class CreateInquiryCommentDTO {
    private String comment;
    private Long inquiryId;
    private Long userId;
}
