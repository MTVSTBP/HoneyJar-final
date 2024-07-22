package com.tbp.honeyjar.admin.dto.qna;

import lombok.Data;

@Data
public class QnaResponseDto {

    private long id;
    private String title;
    private String post;
    private int categoryId;
}
