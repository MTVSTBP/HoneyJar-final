package com.tbp.honeyjar.mypage.DTO;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class MyPageCorrectionDTO {
    private Long userId;
    private String userName;
    private String userPr;
    private String profileImage;

    public MyPageCorrectionDTO(Long userId, String userName, String userPr, String profileImage) {
        this.userId = userId;
        this.userName = userName;
        this.userPr = userPr;
        this.profileImage = profileImage;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPr() {
        return userPr;
    }

    public String getProfileImage() {
        return profileImage;
    }


    @Override
    public String toString() {
        return "MyPageCorrectionDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPr='" + userPr + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}