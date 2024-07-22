package com.tbp.honeyjar.admin.dto.category;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QnaCategorySaveRequestDto {

    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
