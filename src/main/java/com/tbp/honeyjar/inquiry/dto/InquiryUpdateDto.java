package com.tbp.honeyjar.inquiry.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InquiryUpdateDto {
    private Long id; // 문의 번호
    private String title; // 제목
    private String post; // 내용
    private Long categoryId; // 카테고리
}
