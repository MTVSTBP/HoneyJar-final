package com.tbp.honeyjar.admin.dao;

import com.tbp.honeyjar.admin.dto.category.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<FoodCategoryListResponseDto> findAllFoodCategory();

    List<QnaCategoryListResponseDto> findAllQnaCategory();

    FoodResponseDto findFoodById(Long id);

    QnaResponseDto findQnaById(Long id);

    Long saveFoodCategory(FoodCategorySaveRequestDto requestDto);

    Long saveQnaCategory(QnaCategorySaveRequestDto requestDto);
}
