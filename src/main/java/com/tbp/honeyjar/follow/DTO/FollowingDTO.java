package com.tbp.honeyjar.follow.DTO;

import lombok.Builder;

import java.util.List;

public class FollowingDTO {
    private Long userId;
    private List<FollowDTO> followees;

    @Builder
    public FollowingDTO(Long userId, List<FollowDTO> followees) {
        this.userId = userId;
        this.followees = followees;
    }

    public Long getUserId() {
        return userId;
    }

    public List<FollowDTO> getFollowees() {
        return followees;
    }

    @Override
    public String toString() {
        return "FolloweeDTO{" +
                "userId=" + userId +
                ", followers=" + followees +
                '}';
    }
}