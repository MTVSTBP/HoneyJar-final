package com.tbp.honeyjar.admin.dto;

import lombok.Data;

@Data
public class QnADto {

    private long id;
    private String comment_title;
    private String content;

    public QnADto(long id, String comment_title, String content) {
        this.id = id;
        this.comment_title = comment_title;
        this.content = content;
    }
}
