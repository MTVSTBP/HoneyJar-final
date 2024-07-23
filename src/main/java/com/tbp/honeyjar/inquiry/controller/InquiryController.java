package com.tbp.honeyjar.inquiry.controller;

import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryWriteDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import com.tbp.honeyjar.inquiry.service.InquiryService;
import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import com.tbp.honeyjar.login.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/settings/inquiry")
public class InquiryController {
    private InquiryService inquiryService;
    private UserService userService;
    private UserMapper userMapper;

    public InquiryController(InquiryService inquiryService, UserService userService, UserMapper userMapper) {
        this.inquiryService = inquiryService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String getInquiriesByUserId(Model model, Principal principal) {
        String kakaoId = principal.getName();
        List<InquiryDto> inquiryList = inquiryService.getInquiryListByUserId(kakaoId);
        model.addAttribute("inquiryList", inquiryList);
        return "pages/inquiry/inquiry";
    }

    @GetMapping("/write")
    public String inquiryWrite(Model model, Principal principal) {
        String kakaoId = principal.getName();
        return "pages/inquiry/inquiryWrite";
    }

    @PostMapping("/write")
    public String submitInquiry(@RequestParam("title") String title,
                                @RequestParam("category") String category,
                                @RequestParam("post") String post,
                                Principal principal) {
        String kakaoId = principal.getName();
        Long userId = userMapper.findByKakaoId(kakaoId).getUserId();
        InquiryWriteDto inquiryWriteDto = InquiryWriteDto.builder()
                .userId(userId)
                .title(title)
                .categoryId(Long.parseLong(category))
                .post(post)
                .build();
        inquiryService.createInquiry(inquiryWriteDto);
        return "redirect:/settings/inquiry";
    }

    //principl 을 사용하여 id찾기
    //
    @GetMapping("/detail/{inquiryId}")
    public String getInquiryDetail(@PathVariable Long inquiryId, Model model, Principal principal) {
        String kakaoId = principal.getName();
        Long userId = userMapper.findByKakaoId(kakaoId).getUserId();
        InquiryDto inquiry = inquiryService.getInquiryById(inquiryId);

        if (!inquiry.getUserId().equals(userId)) {
            return "redirect:/settings/inquiry";
        }
        model.addAttribute("inquiry", inquiry);
        return "pages/inquiry/inquiryDetail";
    }

    @GetMapping("/correction")
    public String inquiryCorrection(Model model, Principal principal) {
        String kakaoId = principal.getName();
        Long userId = userMapper.findByKakaoId(kakaoId).getUserId();
        InquiryDto inquiry = inquiryService.getInquiryById(userId);
        model.addAttribute("inquiry", inquiry);
        return "pages/inquiry/inquiryCorrection";
    }

    @PostMapping("/correction")
    public String updateInquiry(@RequestParam("title") String title,
                                @RequestParam("category") String category,
                                @RequestParam("post") String post,
                                Principal principal) {
        String kakaoId = principal.getName();
        Long userId = userMapper.findByKakaoId(kakaoId).getUserId();
        InquiryUpdateDto inquiryUpdateDto = InquiryUpdateDto.builder()
                .id(userId)
                .title(title)
                .categoryId(Long.parseLong(category))
                .post(post)
                .build();
        inquiryService.updateInquiry(inquiryUpdateDto);
        return "redirect:/settings/inquiry/detail";
    }

    @PostMapping("/delete")
    public String deleteInquiry(Principal principal) {
        String kakaoId = principal.getName();
        Long userId = userMapper.findByKakaoId(kakaoId).getUserId();
        InquiryDto inquiry = inquiryService.getInquiryById(userId);

        inquiryService.deleteInquiry(userId);
        return "redirect:/settings/inquiry";
    }
}
