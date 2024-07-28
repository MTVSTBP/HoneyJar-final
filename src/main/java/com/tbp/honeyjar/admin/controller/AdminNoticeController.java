package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.notice.NoticeCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.AdminNoticeResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeSaveRequestDto;
import com.tbp.honeyjar.admin.service.AdminNoticeService;
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
@RequestMapping("/admin/notice")
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;

    @GetMapping
    public String notice(Model model) {

        List<AdminNoticeListResponseDto> noticeList = adminNoticeService.findAllNotices();

        if (!noticeList.isEmpty()) {
            model.addAttribute("noticeList", noticeList);
        }

        return "pages/admin/notice/adminNotice";
    }

    @GetMapping("/{notice_id}")
    public String noticeDetail(@PathVariable Long notice_id, Model model) {

        AdminNoticeResponseDto notice = adminNoticeService.findById(notice_id);

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

