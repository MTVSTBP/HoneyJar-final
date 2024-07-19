package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.category.*;
import com.tbp.honeyjar.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/write/food")
    public String writeFood() {
        return "pages/admin/category/adminCategoryWrite";
    }

    @PostMapping("/write/food")
    public String writeFoodCategory(FoodCategorySaveRequestDto requestDto) {

        requestDto.setCreatedAt(LocalDateTime.now());
        requestDto.setUpdatedAt(LocalDateTime.now());

        categoryService.saveFoodCategory(requestDto);

        return "redirect:/admin/category";
    }

    @GetMapping("/write/qna")
    public String writeQna() {
        return "pages/admin/category/adminCategoryWrite";
    }

    @PostMapping("/write/qna")
    public String writeQnaCategory(QnaCategorySaveRequestDto requestDto) {

        requestDto.setCreatedAt(LocalDateTime.now());
        requestDto.setUpdatedAt(LocalDateTime.now());

        categoryService.saveQnaCategory(requestDto);

        return "redirect:/admin/category";
    }

    @GetMapping("/correction/food/{id}")
    public String correctionFood(@PathVariable Long id, Model model) {

        FoodResponseDto data = categoryService.findFoodById(id);

        model.addAttribute("data", data);

        return "pages/admin/category/adminCategoryCorrection";
    }

    @GetMapping("/correction/qna/{id}")
    public String correctionQna(@PathVariable Long id, Model model) {

        QnaResponseDto data = categoryService.findQnaById(id);

        model.addAttribute("data", data);

        return "pages/admin/category/adminCategoryCorrection";
    }
}
