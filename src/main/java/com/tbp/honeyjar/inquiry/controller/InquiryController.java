package com.tbp.honeyjar.inquiry.controller;

import com.tbp.honeyjar.admin.dto.category.QnaCategoryListResponseDto;
import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryWriteDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import com.tbp.honeyjar.inquiry.service.InquiryService;
import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import com.tbp.honeyjar.login.service.user.UserService;
import com.tbp.honeyjar.mypage.DTO.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private CategoryService categoryService;

    public InquiryController(InquiryService inquiryService, UserService userService, UserMapper userMapper, CategoryService categoryService) {
        this.inquiryService = inquiryService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.categoryService = categoryService;
    }

    //    @GetMapping
//    public String getInquiriesByUserId(Model model, Principal principal) {
//        String kakaoId = principal.getName();
//        List<InquiryDto> inquiryList = inquiryService.getInquiryListByUserId(kakaoId);
//        model.addAttribute("inquiryList", inquiryList);
//        return "pages/inquiry/inquiry";
//    }
    @GetMapping
    public String getInquiriesByUserId(Model model, Principal principal, @RequestParam(defaultValue = "1") int page) {
        String kakaoId = principal.getName();
        page = page < 1 ? 1 : page;
        Pageable pageable = PageRequest.of(page - 1, 5);  // 페이지 번호는 0부터 시작하므로 -1
        Page<InquiryDto> inquiryPage = inquiryService.getInquiryListByUserId(kakaoId, pageable);
        model.addAttribute("inquiryList", inquiryPage.getContent());
        page = page > inquiryPage.getTotalPages() ? inquiryPage.getTotalPages() : page;

        model.addAttribute("page", page);
        model.addAttribute("totalPages", inquiryPage.getTotalPages());

        return (page > inquiryPage.getTotalPages()) ?
                "redirect:settings/inquiry?page=" + page :
                "pages/inquiry/inquiry";

    }


    @GetMapping("/write")
    public String inquiryWrite(Model model, Principal principal) {
        String kakaoId = principal.getName();
        List<QnaCategoryListResponseDto> categories = categoryService.findAllQnaCategory();
        model.addAttribute("categories", categories);
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
    public String getInquiryDetail(@PathVariable Long inquiryId,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   Model model, Principal principal) {
        String kakaoId = principal.getName();
        User userInfo = userMapper.findByKakaoId(kakaoId);
        InquiryDto inquiry = inquiryService.getInquiryById(inquiryId);

        if (!inquiry.getUserId().equals(userInfo.getUserId())) {
            return "redirect:/settings/inquiry";
        }
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("username", userInfo.getName());
        model.addAttribute("page", page);
        return "pages/inquiry/inquiryDetail";
    }

    @GetMapping("/correction/{inquiryId}")
    public String inquiryCorrection(@PathVariable Long inquiryId, Model model, Principal principal) {
        String kakaoId = principal.getName();
        User userInfo = userMapper.findByKakaoId(kakaoId);
        InquiryDto inquiry = inquiryService.getInquiryById(inquiryId);
        List<QnaCategoryListResponseDto> categories = categoryService.findAllQnaCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("username", userInfo.getName());
        return "pages/inquiry/inquiryCorrection";
    }

    @PostMapping("/correction/{inquiryId}")
    public String updateInquiry(@PathVariable Long inquiryId,
                                @RequestParam("title") String title,
                                @RequestParam("category") String category,
                                @RequestParam("post") String post,
                                Principal principal) {
        String kakaoId = principal.getName();
        InquiryDto inquiry = inquiryService.getInquiryById(inquiryId);
        User userInfo = userMapper.findByKakaoId(kakaoId);
        if (!inquiry.getUserId().equals(userInfo.getUserId())) {
            return "redirect:/settings/inquiry";
        }
        InquiryUpdateDto inquiryUpdateDto = InquiryUpdateDto.builder()
                .inquiryId(inquiryId)
                .title(title)
                .categoryId(Long.parseLong(category))
                .post(post)
                .build();
        inquiryService.updateInquiry(inquiryUpdateDto);
        return "redirect:/settings/inquiry/detail/" + inquiryId;
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
