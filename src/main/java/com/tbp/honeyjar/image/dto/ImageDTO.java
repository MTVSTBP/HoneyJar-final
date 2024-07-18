package com.tbp.honeyjar.image.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 포함하는 생성자 추가
public class ImageDTO {
    private Long imageId;
    private String url;
    private Long userId;
    private Long postId;
    private boolean isMain;
}