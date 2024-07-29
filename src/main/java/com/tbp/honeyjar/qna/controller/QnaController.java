package com.tbp.honeyjar.qna.controller;

import com.tbp.honeyjar.admin.dto.category.QnaCategoryListResponseDto;
import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.qna.dto.QnaListResponseDto;
import com.tbp.honeyjar.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/settings/qna")
public class QnaController {

    private final QnaService qnaService;
    private final CategoryService categoryService;

    @GetMapping
    public String qna(Model model) {
        List<QnaListResponseDto> qnaList = qnaService.findAllQna();
        List<QnaCategoryListResponseDto> qnaCategory = categoryService.findAllQnaCategory();

        model.addAttribute("qnaList", qnaList);
        model.addAttribute("qnaCategory", qnaCategory);

        return "pages/qna/qna";
    }
}
