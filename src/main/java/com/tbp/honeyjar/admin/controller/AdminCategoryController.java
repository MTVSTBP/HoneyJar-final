package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.category.CategorySaveRequestDto;
import com.tbp.honeyjar.admin.dto.category.FoodCategoryListResponseDto;
import com.tbp.honeyjar.admin.dto.category.QnaCategoryListResponseDto;
import com.tbp.honeyjar.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String category(Model model) {

        List<FoodCategoryListResponseDto> foodList = categoryService.findAllFoodCategory();
        List<QnaCategoryListResponseDto> qnaList = categoryService.findAllQnaCategory();

        model.addAttribute("foodList", foodList);
        model.addAttribute("qnaList", qnaList);

        return "pages/admin/category/adminCategory";
    }

    @GetMapping("/write")
    public String write() {
        return "pages/admin/category/adminCategoryWrite";
    }

    @PostMapping("/write")
    public String writeCategory(CategorySaveRequestDto requestDto) {

        requestDto.setCreatedAt(LocalDateTime.now());
        requestDto.setUpdatedAt(LocalDateTime.now());

//        CategoryService.save(requestDto);

        return "redirect:/admin/category";
    }
}
