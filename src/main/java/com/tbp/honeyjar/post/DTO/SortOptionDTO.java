package com.tbp.honeyjar.post.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SortOptionDTO {
    private String id;
    private String name;

    @Builder
    public SortOptionDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}