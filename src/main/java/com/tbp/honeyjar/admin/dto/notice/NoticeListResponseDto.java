package com.tbp.honeyjar.admin.dto.notice;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class NoticeListResponseDto {

    private Long noticeId;
    private String title;
    private String post;
    private LocalDateTime createdAt;
    private String formattedCreatedAt;

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        if (createdAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
            this.formattedCreatedAt = createdAt.format(formatter);
        }
    }
}
