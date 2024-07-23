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
    private List<CategoryDTO> likeCategories;
    private List<CategoryDTO> categories;

    public MyPageCorrectionDTO(Long userId, String userName, String userPr, String profileImage, List<CategoryDTO> likeCategories, List<CategoryDTO> categories) {
        this.userId = userId;
        this.userName = userName;
        this.userPr = userPr;
        this.profileImage = profileImage;
        this.likeCategories = likeCategories;
        this.categories = categories;
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

    public List<CategoryDTO> getLikeCategories() {
        return likeCategories;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "MyPageCorrectionDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPr='" + userPr + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", likeCategories=" + likeCategories +
                ", categories=" + categories +
                '}';
    }
}