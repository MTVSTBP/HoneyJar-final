package com.tbp.honeyjar.admin.dto.qna;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QnaCorrectionRequestDto {

    private Long id;
    private String title;
    private String post;
    private Long categoryId;
    private LocalDateTime updatedAt;
}
