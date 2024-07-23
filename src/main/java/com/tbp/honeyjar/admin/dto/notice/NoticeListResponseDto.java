package com.tbp.honeyjar.admin.dto.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeListResponseDto {

    private int noticeId;
    private String title;
    private String post;
    private LocalDateTime createdAt;

    @Builder
    public NoticeListResponseDto(String title, String post, LocalDateTime createdAt) {
        this.title = title;
        this.post = post;
        this.createdAt = createdAt;
    }
}
