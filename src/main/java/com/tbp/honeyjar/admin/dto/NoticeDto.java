package com.tbp.honeyjar.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeDto {

    private long noticeId;
    private String title;
    private String post;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public NoticeDto(String title, String post, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.post = post;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public NoticeDto toDto() {
        return this.builder()
                .title(title)
                .post(post)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
