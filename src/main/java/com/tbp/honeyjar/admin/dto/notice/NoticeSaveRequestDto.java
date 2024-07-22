package com.tbp.honeyjar.admin.dto.notice;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class NoticeSaveRequestDto {

    private String title;
    private String post;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public NoticeSaveRequestDto(String title, String post, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.post = post;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
