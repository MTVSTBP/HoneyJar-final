package com.tbp.honeyjar.inquiry.controller;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryWriteDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import com.tbp.honeyjar.inquiry.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/settings/inquiry")
public class InquiryController {
    @Autowired
    private InquiryService inquiryService;

    @GetMapping
    public String getInquiries(Model model) {
        List<InquiryDto> inquiryList = inquiryService.getInquiryList();
        model.addAttribute("inquiryList", inquiryList);
        return "pages/inquiry/inquiry";
    }

    @GetMapping("/write")
    public String inquiryWrite() {
        return "pages/inquiry/inquiryWrite";
    }

    @PostMapping("/write")
    public String submitInquiry(@RequestParam("userId") Long userId,
                                @RequestParam("title") String title,
                                @RequestParam("category") String category,
                                @RequestParam("post") String post) {
        InquiryWriteDto inquiryWriteDto = InquiryWriteDto.builder()
                .userId(userId)
                .title(title)
                .categoryId(Long.parseLong(category))
                .post(post)
                .build();
        inquiryService.createInquiry(inquiryWriteDto);
        return "redirect:/settings/inquiry";
    }

    @GetMapping("/detail/{id}")
    public String getInquiryDetail(@PathVariable Long id, Model model) {
        InquiryDto inquiry = inquiryService.getInquiryById(id);
        model.addAttribute("inquiry", inquiry);
        return "pages/inquiry/inquiryDetail";
    }

    @GetMapping("/correction/{id}")
    public String inquiryCorrection(@PathVariable Long id, Model model) {
        InquiryDto inquiry = inquiryService.getInquiryById(id);
        model.addAttribute("inquiry", inquiry);
        return "pages/inquiry/inquiryCorrection";
    }

    @PostMapping("/correction/{id}")
    public String updateInquiry(@PathVariable Long id,
                                @RequestParam("title") String title,
                                @RequestParam("category") String category,
                                @RequestParam("post") String post) {
        InquiryUpdateDto inquiryUpdateDto = InquiryUpdateDto.builder()
                .id(id)
                .title(title)
                .categoryId(Long.parseLong(category))
                .post(post)
                .build();
        inquiryService.updateInquiry(inquiryUpdateDto);
        return "redirect:/settings/inquiry/detail/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteInquiry(@PathVariable Long id) {
        inquiryService.deleteInquiry(id);
        return "redirect:/settings/inquiry";
    }
}
