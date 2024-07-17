package com.tbp.honeyjar.mypage.DTO;

import lombok.Builder;

import java.util.List;

public class FollowerDTO{
    private Long userId;
    private List<FollowDTO> followers;

    @Builder
    public FollowerDTO(Long userId, List<FollowDTO> followers) {
        this.userId = userId;
        this.followers = followers;
    }

    public Long getUserId() {
        return userId;
    }

    public List<FollowDTO> getFollowers() {
        return followers;
    }

    @Override
    public String toString() {
        return "FollowerDTO{" +
                "userId=" + userId +
                ", followers=" + followers +
                '}';
    }
}