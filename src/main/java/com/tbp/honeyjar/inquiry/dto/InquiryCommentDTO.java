package com.tbp.honeyjar.inquiry.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InquiryCommentDTO {
    private Long commentId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long inquiryId;
    private Long userId;
    private String name;

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
