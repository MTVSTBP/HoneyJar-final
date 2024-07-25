package com.tbp.honeyjar.follow.dao;

import com.tbp.honeyjar.follow.DTO.FollowerDTO;
import com.tbp.honeyjar.follow.DTO.FollowingDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowMapper {
    FollowerDTO getFollowers(Long userId);
    FollowingDTO getFollowings(Long userId);
}
