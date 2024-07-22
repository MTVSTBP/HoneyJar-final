package com.tbp.honeyjar.admin.dto.notice;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class NoticeSaveRequestDto {

    private String title;
    private String post;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
