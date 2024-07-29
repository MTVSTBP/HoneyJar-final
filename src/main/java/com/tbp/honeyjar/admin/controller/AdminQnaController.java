package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.category.QnaCategoryListResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.qna.AdminQnaListResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaSaveRequestDto;
import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.admin.service.AdminQnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/qna")
public class AdminQnaController {

    private final AdminQnaService adminQnaService;
    private final CategoryService categoryService;

    @GetMapping
    public String qna(Model model) {

        List<AdminQnaListResponseDto> qnaList = adminQnaService.findAllQna();
        List<QnaCategoryListResponseDto> qnaCategory = categoryService.findAllQnaCategory();

        model.addAttribute("qnaList", qnaList);
        model.addAttribute("qnaCategory", qnaCategory);

        return "pages/admin/qna/adminQna";
    }

    @GetMapping("/write")
    public String write(Model model) {

        List<QnaCategoryListResponseDto> qnaCategory = categoryService.findAllQnaCategory();

        model.addAttribute("qnaCategory", qnaCategory);

        return "pages/admin/qna/adminQnaWrite";
    }

    @PostMapping("/write")
    public String write(QnaSaveRequestDto requestDto) {

        requestDto.setUserId(1L);

        adminQnaService.save(requestDto);

        return "redirect:/admin/qna";
    }

    @GetMapping("/correction/{qna_id}")
    public String qnaCorrection(@PathVariable Long qna_id, Model model) {

        QnaResponseDto dto = adminQnaService.findById(qna_id);
        List<QnaCategoryListResponseDto> qnaCategory = categoryService.findAllQnaCategory();

        if (dto != null) {
            model.addAttribute("dto", dto);
            model.addAttribute("qnaCategory", qnaCategory);
        }

        return "pages/admin/qna/adminQnaCorrection";
    }

    @PostMapping("/correction/{qna_id}")
    public String qnaCorrection(@PathVariable Long qna_id, QnaCorrectionRequestDto requestDto) {

        QnaResponseDto qna = adminQnaService.findById(qna_id);

        if (qna != null) {
            requestDto.setId(qna.getId());
            adminQnaService.correction(requestDto);
        }

        return "redirect:/admin/qna";
    }

    @GetMapping("/delete/{qna_id}")
    public String delete(@PathVariable Long qna_id) {

        QnaResponseDto qna = adminQnaService.findById(qna_id);

        if (qna != null) {
            adminQnaService.delete(qna_id);
        }

        return "redirect:/admin/qna";
    }
}
