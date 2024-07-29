package com.tbp.honeyjar.login.service.user;

import com.tbp.honeyjar.login.DTO.UserDTO;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Long findUserIdByKakaoId(String kakaoId) {
        return userMapper.findUserIdByKakaoId(kakaoId);
    }

    public void deleteUser(String kakaoId) {
        userMapper.deleteUser(kakaoId, LocalDate.now());
    }

    // 추가
    public List<UserDTO> searchUsersByName(String name) {
        return userMapper.searchUsersByName(name);
    }
}
