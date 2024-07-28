package com.tbp.honeyjar.follow.service;

import com.tbp.honeyjar.follow.DTO.FollowerDTO;
import com.tbp.honeyjar.follow.DTO.FollowingDTO;
import com.tbp.honeyjar.follow.dao.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowMapper followMapper;
    public FollowerDTO getFollower(Long userId) {
        FollowerDTO followerDTO = new FollowerDTO();
        followerDTO.setUserId(userId);
        followerDTO.setFollowers(followMapper.getFollower(userId));
        return followerDTO;
    }
    public FollowingDTO getFollowing(Long userId) {
        return followMapper.getFollowings(userId);
    }

    // 추가
    public void followUser(Long userId, Long followUserId) {
        followMapper.insertFollow(userId, followUserId);
    }

    public void unfollowUser(Long userId, Long followUserId) {
        followMapper.deleteFollow(userId, followUserId);
    }

    public boolean isFollowing(Long userId, Long targetUserId) {
        return followMapper.isFollowing(userId, targetUserId);
    }
}
