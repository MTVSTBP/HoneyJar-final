package com.tbp.honeyjar.admin.service;

import com.tbp.honeyjar.admin.dao.CategoryMapper;
import com.tbp.honeyjar.admin.dto.category.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {

    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<FoodCategoryListResponseDto> findAllFoodCategory() {
        return categoryMapper.findAllFoodCategory();
    }

    @Transactional(readOnly = true)
    public List<QnaCategoryListResponseDto> findAllQnaCategory() {
        return categoryMapper.findAllQnaCategory();
    }

    @Transactional(readOnly = true)
    public FoodResponseDto findFoodById(Long id) {
        return categoryMapper.findFoodById(id);
    }

    @Transactional(readOnly = true)
    public QnaResponseDto findQnaById(Long id) {
        return categoryMapper.findQnaById(id);
    }

    public Long saveFoodCategory(FoodCategorySaveRequestDto requestDto) {
        return categoryMapper.saveFoodCategory(requestDto);
    }

    public Long saveQnaCategory(QnaCategorySaveRequestDto requestDto) {
        return categoryMapper.saveQnaCategory(requestDto);
    }

    public Long correctionFoodCategory(FoodCategoryCorrectionDto requestDto) {
        return categoryMapper.correctionFoodCategory(requestDto);
    }

    public Long correctionQnaCategory(QnaCategoryCorrectionDto requestDto) {
        return categoryMapper.correctionQnaCategory(requestDto);
    }
}
