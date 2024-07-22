package com.tbp.honeyjar.mypage.dao;

import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageMapper {
    MyPageDTO getUserDetail(Long userId);
    void updateMyPage(MyPageDTO myPageDTO);
}