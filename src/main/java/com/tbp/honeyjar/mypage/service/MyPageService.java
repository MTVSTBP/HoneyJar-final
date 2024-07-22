package com.tbp.honeyjar.mypage.service;

import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.dao.MyPageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public MyPageDTO getUserDetail(Long userId) {
        return myPageMapper.getUserDetail(userId);
    }
}