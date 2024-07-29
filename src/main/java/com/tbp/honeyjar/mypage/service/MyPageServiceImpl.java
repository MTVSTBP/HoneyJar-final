package com.tbp.honeyjar.mypage.service;

import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.repository.MyPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageServiceImpl implements MyPageService {

    private final MyPageRepository myPageRepository;

    @Autowired
    public MyPageServiceImpl(MyPageRepository myPageRepository) {
        this.myPageRepository = myPageRepository;
    }

    @Override
    public MyPageDTO getMyPage(Long userId) {
        return myPageRepository.findByUserId(userId);
    }

    @Override
    public void updateMyPage(MyPageDTO myPageDTO) {
        myPageRepository.updateMyPage(myPageDTO);
    }
}