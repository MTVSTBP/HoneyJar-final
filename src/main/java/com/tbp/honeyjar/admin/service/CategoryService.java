package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.CategoryMapper;
import com.tbp.honeyjar.admin.dto.category.FoodCategorySaveRequestDto;
import com.tbp.honeyjar.admin.dto.category.FoodCategoryListResponseDto;
import com.tbp.honeyjar.admin.dto.category.QnaCategoryListResponseDto;
import com.tbp.honeyjar.admin.dto.category.QnaCategorySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public List<FoodCategoryListResponseDto> findAllFoodCategory() {
        return categoryMapper.findAllFoodCategory();
    }

    public List<QnaCategoryListResponseDto> findAllQnaCategory() {
        return categoryMapper.findAllQnaCategory();
    }

    public Long saveFoodCategory(FoodCategorySaveRequestDto requestDto) {
        return categoryMapper.saveFoodCategory(requestDto);
    }

    public Long saveQnaCategory(QnaCategorySaveRequestDto requestDto) {
        return categoryMapper.saveQnaCategory(requestDto);
    }
}
