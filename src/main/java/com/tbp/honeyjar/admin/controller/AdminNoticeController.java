package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.notice.NoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeResponseDto;
import com.tbp.honeyjar.admin.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/notice")
public class AdminNoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String notice(Model model) {

        List<NoticeListResponseDto> noticeList = noticeService.findAllNotices();

        if (!noticeList.isEmpty()) {
            model.addAttribute("noticeList", noticeList);
        }

        return "pages/admin/notice/adminNotice";
    }

    @GetMapping("/{notice_id}")
    public String noticeDetail(@PathVariable Long notice_id, Model model) {
        NoticeResponseDto notice = noticeService.findById(notice_id);

        model.addAttribute("notice", notice);

        return "pages/admin/notice/adminNoticeDetail";
    }

    @GetMapping("/write/{notice_id}")
    public String write(@PathVariable Long notice_id, Model model) {
        NoticeResponseDto notice = noticeService.findById(notice_id);

        model.addAttribute("notice", notice);

        return "pages/admin/notice/adminNoticeCorrection";
    }
}

