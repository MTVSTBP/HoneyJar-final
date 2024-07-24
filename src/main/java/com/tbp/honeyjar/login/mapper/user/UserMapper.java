package com.tbp.honeyjar.login.mapper.user;

import com.tbp.honeyjar.login.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

@Mapper
public interface UserMapper {
    User getUserByUserId(Long userId);
    User findByUserName(String name);
    User findByKakaoId(String kakaoId);
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(String kakaoId, LocalDate firedAt);
    void reactivateUser(User user);
    Long findUserIdByKakaoId(String kakaoId);
}
