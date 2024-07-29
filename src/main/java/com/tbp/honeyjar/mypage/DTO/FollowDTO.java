package com.tbp.honeyjar.mypage.DTO;

import lombok.Builder;

public class FollowDTO {
    private Long userId;
    private String userName;
    private String profileImage;
    private boolean requestStatus;

    @Builder
    public FollowDTO(Long userId, String userName, String profileImage, boolean requestStatus) {
        this.userId = userId;
        this.userName = userName;
        this.profileImage = profileImage;
        this.requestStatus = requestStatus;
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

    public boolean isRequestStatus() {
        return requestStatus;
    }

    @Override
    public String toString() {
        return "FollowDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", requestStatus=" + requestStatus +
                '}';
    }
}