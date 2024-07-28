package com.tbp.honeyjar.mypage.service;

import com.tbp.honeyjar.mypage.DTO.CategoryDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageCorrectionDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.DTO.PostDTO;
import com.tbp.honeyjar.mypage.dao.MyPageMapper;
import com.tbp.honeyjar.post.dto.PostListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public MyPageDTO getMyPage(Long userId) {
        return myPageMapper.getUserDetail(userId);
    }
    public List<CategoryDTO> getCategoryList() {return myPageMapper.getCategoryList();}
    public List<PostListDTO> getMyPost(Long userId) {return myPageMapper.getMyPost(userId);}
    public MyPageCorrectionDTO getMyProfile(Long userId){return myPageMapper.getMyProfile(userId);}
    public void updateMyProfile(MyPageCorrectionDTO myPageCorrectionDTO) {myPageMapper.updateMyPage(myPageCorrectionDTO);}
    public List<PostListDTO> getMyBookmark(Long category, Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        return myPageMapper.getMyBookmark(params, userId);
    }
    public List<PostListDTO> getMyPosts(Long category, Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        return myPageMapper.getMyPosts(params, userId);
    }
}