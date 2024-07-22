package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.qna.QnaListResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaResponseDto;
import com.tbp.honeyjar.admin.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/qna")
public class AdminQnaController {

    private final QnaService qnaService;

    @GetMapping
    public String qna(Model model) {

        List<QnaListResponseDto> qnaList = qnaService.findAllQna();

        model.addAttribute("qnaList", qnaList);

        return "pages/admin/qna/adminQna";
    }

    @GetMapping("/write")
    public String write() {
        return "pages/admin/qna/adminQnaWrite";
    }

    @GetMapping("/correction")
    public String correction() {
        return "pages/admin/qna/adminQnaCorrection";
    }

    @GetMapping("/correction/{qna_id}")
    public String qnaDetail(@PathVariable Long qna_id, Model model) {
        QnaResponseDto dto = qnaService.findById(qna_id);

        if (dto != null) {
            model.addAttribute("dto", dto);
        }

        return "pages/admin/qna/adminQnaCorrection";
    }
}
