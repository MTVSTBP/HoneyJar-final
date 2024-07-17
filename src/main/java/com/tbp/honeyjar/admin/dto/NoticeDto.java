package com.tbp.honeyjar.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeDto {

    private long noticeId;
    private String title;
    private String post;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
