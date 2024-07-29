package com.tbp.honeyjar.comment.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentListDTO {
    private Long commentId = 1L; // 사용자 아이디
    private String nickName = "nickName"; // 사용자 닉네임
    private String profileImg = "/assets/svg/base_profile.svg"; // 사용자 프로필
    private String content = "comment"; // 댓글 내용
    private String date;

    @Builder
    public CommentListDTO(Long commentId, String nickName, String profileImg, String content, String date) {
        this.commentId = commentId;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.content = content;
        this.date = date;
    }
}
