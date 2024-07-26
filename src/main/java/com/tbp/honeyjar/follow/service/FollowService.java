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
    public FollowerDTO getFollowers(Long userId) {
        return followMapper.getFollowers(userId);
    }
    public FollowingDTO getFollowing(Long userId) {
        return followMapper.getFollowings(userId);
    }
}
