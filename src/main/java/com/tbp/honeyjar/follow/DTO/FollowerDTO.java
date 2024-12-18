package com.tbp.honeyjar.follow.DTO;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class FollowerDTO{
    private Long userId;
    private List<FollowDTO> followers;

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