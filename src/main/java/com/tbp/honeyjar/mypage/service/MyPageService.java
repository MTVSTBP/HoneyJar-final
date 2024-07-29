package com.tbp.honeyjar.mypage.service;

import com.tbp.honeyjar.mypage.DTO.MyPageDTO;

import java.util.List;

public interface MyPageService {
    MyPageDTO getMyPage(Long userId);
    void updateMyPage(MyPageDTO myPageDTO);

}