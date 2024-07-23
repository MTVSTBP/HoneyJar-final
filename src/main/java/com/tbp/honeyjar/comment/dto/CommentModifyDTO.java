package com.tbp.honeyjar.comment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Data
public class CommentModifyDTO {
    private Long commentId;
    private String comment;
}