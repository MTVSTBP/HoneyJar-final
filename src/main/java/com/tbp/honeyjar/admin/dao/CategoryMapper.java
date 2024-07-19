package com.tbp.honeyjar.admin.dao;

import com.tbp.honeyjar.admin.dto.category.FoodCategoryListResponseDto;
import com.tbp.honeyjar.admin.dto.category.FoodCategorySaveRequestDto;
import com.tbp.honeyjar.admin.dto.category.QnaCategoryListResponseDto;
import com.tbp.honeyjar.admin.dto.category.QnaCategorySaveRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<FoodCategoryListResponseDto> findAllFoodCategory();

    List<QnaCategoryListResponseDto> findAllQnaCategory();

    Long saveFoodCategory(FoodCategorySaveRequestDto requestDto);

    Long saveQnaCategory(QnaCategorySaveRequestDto requestDto);
}
