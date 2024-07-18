package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.NoticeDto;
import com.tbp.honeyjar.admin.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/notice")
public class AdminNoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String notice(Model model) {

        List<NoticeDto> noticeList = noticeService.findAllNotices();

        if (!noticeList.isEmpty()) {
            model.addAttribute("noticeList", noticeList);
        }

        return "pages/admin/notice/adminNotice";
    }

    @GetMapping("/write")
    public String write() {
        return "pages/admin/notice/adminNoticeWrite";
    }

    @GetMapping("/write/{notice_id}")
    public String write(@PathVariable Long notice_id) {
//        noticeService.findById(notice_id);
        return "pages/admin/notice/adminNoticeDetail";
    }
}

