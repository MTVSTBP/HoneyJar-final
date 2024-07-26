package com.tbp.honeyjar.follow.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
public class FollowDTO {
    private Long userId;
    private String userName;
    private String profileImage;

    @Builder
    public FollowDTO(Long userId, String userName, String profileImage, boolean requestStatus) {
        this.userId = userId;
        this.userName = userName;
        this.profileImage = profileImage;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImage() {
        return profileImage;
    }


    @Override
    public String toString() {
        return "FollowDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}