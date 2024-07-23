package com.tbp.honeyjar.admin.dto.qna;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QnaSaveRequestDto {

    private Long id;
    private String title;
    private String post;
    private Long categoryId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
