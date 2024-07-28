package com.tbp.honeyjar.qna.dto;

import lombok.Data;

@Data
public class QnaListResponseDto {

    private Long id;
    private String title;
    private String post;
    private Long categoryId;
}
