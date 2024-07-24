package com.tbp.honeyjar.admin.dto.notice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeResponseDto {

    private Long noticeId;
    private String title;
    private String post;
    private LocalDateTime createdAt;
}
