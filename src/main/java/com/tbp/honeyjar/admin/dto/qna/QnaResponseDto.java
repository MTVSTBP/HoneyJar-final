package com.tbp.honeyjar.admin.dto.qna;

import lombok.Data;

@Data
public class QnaResponseDto {

    private Long id;
    private String title;
    private String post;
    private Long categoryId;
}
