package com.tbp.honeyjar.admin.dto.category;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodCategoryCorrectionDto {

    private Long id;
    private String name;
    private LocalDateTime updatedAt;
}
