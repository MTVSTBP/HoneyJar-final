package com.tbp.honeyjar.mypage.dao;

import com.tbp.honeyjar.mypage.DTO.CategoryDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageCorrectionDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.DTO.PostDTO;
import com.tbp.honeyjar.post.dto.PostListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    MyPageDTO getUserDetail(Long userId);
    void updateMyPage(MyPageCorrectionDTO myPageCorrectionDTO);
    List<CategoryDTO> getCategoryList();
    List<PostListDTO> getMyPost(Long userId);
    MyPageCorrectionDTO getMyProfile(Long userId);
}