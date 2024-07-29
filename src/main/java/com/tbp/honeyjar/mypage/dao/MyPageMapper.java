package com.tbp.honeyjar.mypage.dao;

import com.tbp.honeyjar.mypage.DTO.CategoryDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageCorrectionDTO;
import com.tbp.honeyjar.mypage.DTO.MyPageDTO;
import com.tbp.honeyjar.mypage.DTO.PostDTO;
import com.tbp.honeyjar.post.dto.PostListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyPageMapper {
    MyPageDTO getUserDetail(Long userId);
    void updateMyPage(MyPageCorrectionDTO myPageCorrectionDTO);
    List<CategoryDTO> getCategoryList();
    List<PostListDTO> getMyPost(Long userId);
    MyPageCorrectionDTO getMyProfile(Long userId);
    List<PostListDTO> getMyBookmark(@Param("params") Map<String, Object> params, @Param("userId") Long userId);
    List<PostListDTO> getMyPosts(@Param("params") Map<String, Object> params, @Param("userId") Long userId);

}