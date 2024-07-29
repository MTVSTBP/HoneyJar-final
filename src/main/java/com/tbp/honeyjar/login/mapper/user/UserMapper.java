package com.tbp.honeyjar.login.mapper.user;

import com.tbp.honeyjar.login.DTO.UserDTO;
import com.tbp.honeyjar.login.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserMapper {
    User findByUserId(Long userId);
    User findByUserName(String name);
    User findByKakaoId(String kakaoId);
    Long findUserIdByKakaoId(String kakaoId);
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(String kakaoId, LocalDate firedAt);
    void reactivateUser(User user);

    // 추가
    List<UserDTO> searchUsersByName(String name);
}
