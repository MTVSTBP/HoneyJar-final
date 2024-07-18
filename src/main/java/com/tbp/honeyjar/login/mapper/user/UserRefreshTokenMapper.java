package com.tbp.honeyjar.login.mapper.user;

import com.tbp.honeyjar.login.entity.user.UserRefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRefreshTokenMapper {
    UserRefreshToken findByUserId(Long userId);

    void insertUserRefreshToken(UserRefreshToken userRefreshToken);
    void updateUserRefreshToken(UserRefreshToken userRefreshToken);
    void deleteUserRefreshToken(Long userId);
}
