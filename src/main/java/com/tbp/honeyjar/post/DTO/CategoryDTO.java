package com.tbp.honeyjar.post.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDTO {
    private String id;
    private String name;

    @Builder
    public CategoryDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
