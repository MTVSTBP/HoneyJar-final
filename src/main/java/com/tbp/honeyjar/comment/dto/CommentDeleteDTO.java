package com.tbp.honeyjar.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentDeleteDTO {
    private long commentId;
    private long postId;
}
