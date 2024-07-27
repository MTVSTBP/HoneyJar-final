package com.tbp.honeyjar.follow.dao;

import com.tbp.honeyjar.follow.DTO.FollowDTO;
import com.tbp.honeyjar.follow.DTO.FollowerDTO;
import com.tbp.honeyjar.follow.DTO.FollowingDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {
    List<FollowDTO> getFollower(Long userId);
    FollowingDTO getFollowings(Long userId);
}
