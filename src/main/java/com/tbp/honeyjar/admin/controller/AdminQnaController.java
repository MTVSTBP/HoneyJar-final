package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.qna.QnaListResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaResponseDto;
import com.tbp.honeyjar.admin.dto.qna.QnaSaveRequestDto;
import com.tbp.honeyjar.admin.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.*;

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

    @PostMapping("/write")
    public String write(QnaSaveRequestDto requestDto) {
        requestDto.setCreatedAt(now());
        requestDto.setUpdatedAt(now());
        requestDto.setUserId(1);

        qnaService.save(requestDto);

        return "redirect:/admin/qna";
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
