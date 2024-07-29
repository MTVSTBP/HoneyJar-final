package com.tbp.honeyjar.notice.controller;

import com.tbp.honeyjar.notice.dto.NoticeListResponseDto;
import com.tbp.honeyjar.notice.dto.NoticeResponseDto;
import com.tbp.honeyjar.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/settings/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String notice(Model model) {

        List<NoticeListResponseDto> noticeList = noticeService.findAllNotices();

        if (!noticeList.isEmpty()) {
            model.addAttribute("noticeList", noticeList);
        }

        return "pages/notice/notice";
    }

    @GetMapping("/{notice_id}")
    public String noticeDetail(@PathVariable Long notice_id, Model model) {

        NoticeResponseDto notice = noticeService.findById(notice_id);

        if (notice != null) {
            model.addAttribute("notice", notice);
        }

        return "pages/notice/noticeDetail";
    }
}
