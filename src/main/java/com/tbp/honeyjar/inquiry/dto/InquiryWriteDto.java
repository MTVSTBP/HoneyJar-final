package com.tbp.honeyjar.inquiry.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InquiryWriteDto {
    private String title; // 제목
    private String post; // 내용
    private Long userId; // 유저 번호 (FK)
    private Long categoryId; // 카테고리
}
