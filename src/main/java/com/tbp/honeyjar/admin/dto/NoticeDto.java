package com.tbp.honeyjar.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeDto {

    private long id;
    private String title;
    private String post;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public NoticeDto(long id, String title, String post, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.title = title;
        this.post = post;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
