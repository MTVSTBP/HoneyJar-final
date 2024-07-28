package com.tbp.honeyjar.inquiry.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InquiryDto {
    private Long id; // 문의 번호
    private String title; // 제목
    private String post; // 내용
    private LocalDateTime createdAt; // 생성 일자
    private LocalDateTime updatedAt; // 수정 일자
    private Long userId; // 유저 번호 (FK)
    private Long categoryId; // 카테고리
}
