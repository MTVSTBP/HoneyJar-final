package com.tbp.honeyjar.mypage.repository;

import com.tbp.honeyjar.mypage.DTO.MyPageDTO;

import java.util.List;

public interface MyPageRepository {
    MyPageDTO findByUserId(Long userId);
    void updateMyPage(MyPageDTO myPageDTO);
}