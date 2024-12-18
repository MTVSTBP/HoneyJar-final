package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.category.QnaCategoryListResponseDto;
import com.tbp.honeyjar.admin.service.AdminInquiryService;
import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.inquiry.dto.InquiryDto;
import com.tbp.honeyjar.inquiry.dto.InquiryUpdateDto;
import com.tbp.honeyjar.inquiry.dto.InquiryWriteDto;
import com.tbp.honeyjar.inquiry.service.InquiryService;
import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import com.tbp.honeyjar.login.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/// /admin/inquiry/detail/{inquiry_id}
//        1:1 문의 - 작성 : /admin/inquiry/write
@Controller
@RequestMapping("/admin/inquiry")
public class AdminInquiryController {
    private InquiryService inquiryService;
    private AdminInquiryService adminInquiryService;
    private UserService userService;
    private CategoryService categoryService;

    public AdminInquiryController(InquiryService inquiryService, AdminInquiryService adminInquiryService, UserService userService, CategoryService categoryService) {
        this.inquiryService = inquiryService;
        this.adminInquiryService = adminInquiryService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAdminInquiries(Model model, @RequestParam(defaultValue = "1") int page) {
        page = page < 1 ? 1 : page;
        Pageable pageable = PageRequest.of(page - 1, 5);  // 페이지 번호는 0부터 시작하므로 -1
        Page<InquiryDto> inquiryPage = adminInquiryService.getInquiryList(pageable);
        page = page > inquiryPage.getTotalPages() ? inquiryPage.getTotalPages() : page;

        model.addAttribute("page", page);
        model.addAttribute("inquiryList", inquiryPage.getContent());
        model.addAttribute("totalPages", inquiryPage.getTotalPages());

        return (page > inquiryPage.getTotalPages()) ?
                "redirect:admin/inquiry?page=" + page :
                "pages/admin/inquiry/adminInquiry";

    }

    @GetMapping("/detail/{inquiryId}")
    public String getInquiryDetail(@PathVariable Long inquiryId,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   Model model) {
        InquiryDto inquiry = adminInquiryService.getInquiryById(inquiryId);
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("username", "yunjunsu");
        model.addAttribute("page", page);
        model.addAttribute("categoryName", categoryService.findQnaById(inquiry.getCategoryId()).getName());
        return "pages/admin/inquiry/adminInquiryDetail";
    }
//
//    @PostMapping("/delete/{inquiryId}")
//    public String deleteInquiry(@PathVariable Long inquiryId) {
////        InquiryDto inquiry = AdminInquiryService.getInquiryById(inquiryId);
//        boolean result = adminInquiryService.deleteInquiry(inquiryId);
//        if (result) {
//            return "redirect:/admin/inquiry";
//        } else {
//            return "redirect:/admin/inquiry/detail/" + inquiryId;
//        }
//
//    }

    @DeleteMapping("/delete/{inquiryId}")
    public ResponseEntity<String> deleteInquiry(@PathVariable Long inquiryId, @RequestParam("page") int page) {
        boolean result = adminInquiryService.deleteInquiry(inquiryId);
        if (result) {
            return ResponseEntity.ok("삭제 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
        }
    }
}
