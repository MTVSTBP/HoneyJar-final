package com.tbp.honeyjar.follow.dao;

import com.tbp.honeyjar.follow.DTO.FollowDTO;
import com.tbp.honeyjar.follow.DTO.FollowerDTO;
import com.tbp.honeyjar.follow.DTO.FollowingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowMapper {
    List<FollowDTO> getFollower(Long userId);
//    FollowingDTO getFollowings(Long userId);

    List<FollowDTO> getFollowers(Long userId);
    List<FollowDTO> getFollowing(Long userId);


    // 추가
    void insertFollow(@Param("userId") Long userId, @Param("followUserId") Long followUserId);
    void deleteFollow(@Param("userId") Long userId, @Param("followUserId") Long followUserId);

    boolean isFollowing(@Param("userId") Long userId, @Param("targetUserId") Long targetUserId);

}
