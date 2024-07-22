package com.tbp.honeyjar.admin.controller;

import com.tbp.honeyjar.admin.dto.notice.NoticeCorrectionRequestDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeListResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeResponseDto;
import com.tbp.honeyjar.admin.dto.notice.NoticeSaveRequestDto;
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

import static java.time.LocalDateTime.*;

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

        requestDto.setCreatedAt(now());
        requestDto.setUpdatedAt(now());

        noticeService.save(requestDto);

        return "redirect:/admin/notice";
    }

    @GetMapping("/correction/{notice_id}")
    public String correction(@PathVariable Long notice_id, Model model) {

        NoticeResponseDto notice = noticeService.findById(notice_id);

        if (notice != null) {
            model.addAttribute("notice", notice);
        }

        return "pages/admin/notice/adminNoticeCorrection";
    }

    @PostMapping("/correction/{notice_id}")
    public String correctionNotice(@PathVariable Long notice_id, NoticeCorrectionRequestDto requestDto) {

        NoticeResponseDto notice = noticeService.findById(notice_id);

        if (notice != null) {
            requestDto.setUpdatedAt(now());
            requestDto.setNoticeId(notice.getNoticeId());
            noticeService.correction(requestDto);
        }

        return "redirect:/admin/notice";
    }

    @GetMapping("/delete/{notice_id}")
    public String delete(@PathVariable Long notice_id) {

        NoticeResponseDto notice = noticeService.findById(notice_id);

        if (notice != null) {
            noticeService.delete(notice.getNoticeId());
        }

        return "redirect:/admin/notice";
    }
}

