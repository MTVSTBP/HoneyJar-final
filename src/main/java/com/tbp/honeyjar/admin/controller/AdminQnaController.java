package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.QnADto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/qna")
public class AdminQnaController {

    @GetMapping
    public String qna(Model model) {

        QnADto dto = new QnADto(1L, "Title Test", "Content Test");

        model.addAttribute("dto", dto);

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
}
