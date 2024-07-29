package com.tbp.honeyjar.login.service.user;

import com.tbp.honeyjar.login.mapper.user.UserMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void updateUser(String kakaoId) {
    }

    public void deleteUser(String kakaoId) {
        userMapper.deleteUser(kakaoId, LocalDate.now());
    }
}
