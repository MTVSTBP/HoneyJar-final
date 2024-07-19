package com.tbp.honeyjar.login.mapper.user;

import com.tbp.honeyjar.login.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUserId(Long userId);
    User findByUserName(String name);
    User findByKakaoId(String kakaoId);
    void insertUser(User user);
    void updateUser(User user);
}