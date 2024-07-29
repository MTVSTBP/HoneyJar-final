package com.tbp.honeyjar.mypage.repository;

import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MyPageRepositoryImpl implements MyPageRepository {

    private final Map<Long, MyPageDTO> userPages = new HashMap<>();

    @Override
    public MyPageDTO findByUserId(Long userId) {
        return userPages.get(userId);
    }

    @Override
    public void updateMyPage(MyPageDTO myPageDTO) {
        userPages.put(myPageDTO.getUserId(), myPageDTO);
    }
}