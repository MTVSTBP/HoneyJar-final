package com.tbp.honeyjar.login.service.user;

import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserById(Long userId) {
        return userMapper.getUserByUserId(userId);
    }

    public void deleteUser(String kakaoId) {
        userMapper.deleteUser(kakaoId, LocalDate.now());
    }
}
