package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.notice.NoticeCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeSaveRequestDto;
import com.tbp.honeyjar.admin.service.AdminNoticeService;
import com.tbp.honeyjar.notice.dto.NoticeListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/notice")
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;

    @GetMapping
    public String notice(Model model, @RequestParam(defaultValue = "1") int page) {
        page = page < 1 ? 1 : page;
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<AdminNoticeListResponseDto> noticePage = adminNoticeService.findAllNotices(pageable);
        page = page > noticePage.getTotalPages() ? noticePage.getTotalPages() : page;

        System.out.println("noticePage : " + noticePage.toString());
        if (!noticePage.isEmpty()) {
            model.addAttribute("noticeList", noticePage);
            Page<AdminNoticeListResponseDto> noticeList = adminNoticeService.findAllNotices(pageable);
        }

        model.addAttribute("page", page);
        model.addAttribute("totalPages", noticePage.getTotalPages());

        return (page > noticePage.getTotalPages()) ?
                "redirect:/admin/notice?page=" + page :
                "pages/admin/notice/adminNotice";
    }


    @GetMapping("/{noticeId}")
    public String noticeDetail(@PathVariable Long noticeId, Model model) {

        AdminNoticeResponseDto notice = adminNoticeService.findById(noticeId);

        if (notice != null) {
            model.addAttribute("notice", notice);
        }

        return "pages/admin/notice/adminNoticeDetail";
    }

    @GetMapping("/write")
    public String write() {
        return "pages/admin/notice/adminNoticeWrite";
    }

    @PostMapping("/write")
    public String writeNotice(NoticeSaveRequestDto requestDto) {

        adminNoticeService.save(requestDto);

        return "redirect:/admin/notice";
    }

    @GetMapping("/correction/{notice_id}")
    public String correction(@PathVariable Long notice_id, Model model) {

        AdminNoticeResponseDto notice = adminNoticeService.findById(notice_id);

        if (notice != null) {
            model.addAttribute("notice", notice);
        }

        return "pages/admin/notice/adminNoticeCorrection";
    }

    @PostMapping("/correction/{notice_id}")
    public String correctionNotice(@PathVariable Long notice_id, NoticeCorrectionRequestDto requestDto) {

        AdminNoticeResponseDto notice = adminNoticeService.findById(notice_id);

        if (notice != null) {
            requestDto.setNoticeId(notice.getNoticeId());
            adminNoticeService.correction(requestDto);
        }

        return "redirect:/admin/notice";
    }

    @GetMapping("/delete/{notice_id}")
    public String delete(@PathVariable Long notice_id) {

        AdminNoticeResponseDto notice = adminNoticeService.findById(notice_id);

        if (notice != null) {
            adminNoticeService.delete(notice.getNoticeId());
        }

        return "redirect:/admin/notice";
    }
}

