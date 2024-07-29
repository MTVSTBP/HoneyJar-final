package com.tbp.honeyjar.post.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostClusterDTO {

    private String placeName;
    private int postCount;
    private Double xCooridnate;
    private Double yCooridnate;
}
