package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.CategoryMapper;
import com.tbp.honeyjar.admin.dto.category.CategorySaveRequestDto;
import com.tbp.honeyjar.admin.dto.category.FoodCategoryListResponseDto;
import com.tbp.honeyjar.admin.dto.category.QnaCategoryListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public Long save(CategorySaveRequestDto requestDto) {
        return categoryMapper.save();
    }

    public List<FoodCategoryListResponseDto> findAllFoodCategory() {
        return categoryMapper.findAllFoodCategory();
    }

    public List<QnaCategoryListResponseDto> findAllQnaCategory() {
        return categoryMapper.findAllQnaCategory();
    }
}
