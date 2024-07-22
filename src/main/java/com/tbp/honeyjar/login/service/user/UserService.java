package com.tbp.honeyjar.login.service.user;

import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import com.tbp.honeyjar.login.oauth.handler.AuthenticationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public User getUserByUserId(Long userId) {
        return userMapper.findByUserId(userId);
    }
}
