package com.tbp.honeyjar.admin.dto.qna;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QnaCorrectionRequestDto {

    private long id;
    private String title;
    private String post;
    private int categoryId;
    private LocalDateTime updatedAt;
}
