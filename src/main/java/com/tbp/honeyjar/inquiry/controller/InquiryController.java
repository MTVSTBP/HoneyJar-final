package com.tbp.honeyjar.inquiry.controller;

import com.tbp.honeyjar.admin.dto.category.QnaCategoryListResponseDto;
import com.tbp.honeyjar.admin.service.CategoryService;
import com.tbp.honeyjar.comment.service.CommentService;
import com.tbp.honeyjar.inquiry.dto.*;
import com.tbp.honeyjar.inquiry.service.InquiryService;
import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/settings/inquiry")
public class InquiryController {
    private InquiryService inquiryService;
    private UserService userService;
    private CategoryService categoryService;
    private CommentService commentService;

    public InquiryController(InquiryService inquiryService, UserService userService, CategoryService categoryService, CommentService commentService) {
        this.inquiryService = inquiryService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.commentService = commentService;
    }

    @GetMapping
    public String getInquiriesByUserId(Model model, Principal principal, @RequestParam(defaultValue = "1") int page) {
        String kakaoId = principal.getName();
        /* Handling Exception minumem page value  */
        page = page < 1 ? 1 : page;

        /* Setting Pageable & Call Service   */
        Pageable pageable = PageRequest.of(page - 1, 5);  // 페이지 번호는 0부터 시작하므로 -1
        Page<InquiryDto> inquiryPage = inquiryService.getInquiryListByUserId(kakaoId, pageable);

        /* Handling Exception maxmum page value  */
        page = page > inquiryPage.getTotalPages() ? inquiryPage.getTotalPages() : page;

        /* Setting Model*/
        model.addAttribute("inquiryList", inquiryPage.getContent());
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
        Long userId = inquiryService.findByKakaoId(kakaoId).getUserId();
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
    @GetMapping("/detail/{inquiryId}")
    public String getInquiryDetail(@PathVariable Long inquiryId,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   Model model, Principal principal) {
        List<InquiryCommentDTO> inquiryCommentList = commentService.getCommentListInquiryId(inquiryId);

        String kakaoId = principal.getName();
        User userInfo = inquiryService.findByKakaoId(kakaoId);
        InquiryDto inquiry = inquiryService.getInquiryById(inquiryId);

        if (!inquiry.getUserId().equals(userInfo.getUserId())) {
            return "redirect:/settings/inquiry";
        }

        model.addAttribute("comments", inquiryCommentList);
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("username", userInfo.getName());
        model.addAttribute("page", page);
        model.addAttribute("categoryName", categoryService.findQnaById(inquiry.getCategoryId()).getName());
        return "pages/inquiry/inquiryDetail";
    }

//    @PostMapping("/comment/{inquiryId}")
//    public String inquiryRegistComment(@ModelAttribute CreateInquiryCommentDTO createInquiryCommentDTO, )

    @PostMapping("/comment")
    @ResponseBody
    public Map<String, Object> submitComment(@RequestBody CreateInquiryCommentDTO createInquiryCommentDTO, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        try {
            String kakaoId = principal.getName();
            User userInfo = inquiryService.findByKakaoId(kakaoId);

            // Validate user
            if (!userInfo.getUserId().equals(createInquiryCommentDTO.getUserId())) {
                response.put("success", false);
                return response;
            }

            commentService.inquiryCreateComment(createInquiryCommentDTO);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
        }
        return response;
    }


    @GetMapping("/correction/{inquiryId}")
    public String inquiryCorrection(@PathVariable Long inquiryId, Model model, Principal principal) {
        String kakaoId = principal.getName();
        User userInfo = inquiryService.findByKakaoId(kakaoId);
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
        User userInfo = inquiryService.findByKakaoId(kakaoId);
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

    public User IsVaildUserId(String kakaoId, Long userId) {
        User userInfo = inquiryService.findByKakaoId(kakaoId);

        //Validate userInfo with kakaoId
        if (!userInfo.getUserId().equals(userId)) {
            return null;
        }
        return userInfo;
    }
}
