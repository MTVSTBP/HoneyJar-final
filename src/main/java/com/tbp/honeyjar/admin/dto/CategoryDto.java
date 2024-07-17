package com.tbp.honeyjar.admin.dto;

import lombok.Data;

@Data
public class CategoryDto {

    private long id;
    private String text;

    public CategoryDto(long id, String text) {
        this.id = id;
        this.text = text;
    }
}
