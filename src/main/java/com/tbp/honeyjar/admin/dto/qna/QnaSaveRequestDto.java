package com.tbp.honeyjar.admin.dto.qna;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QnaSaveRequestDto {

    private long id;
    private String title;
    private String post;
    private int categoryId;
    private int userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
