package com.tbp.honeyjar.notice.controller;

import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import com.tbp.honeyjar.notice.dto.NoticeListResponseDto;
import com.tbp.honeyjar.notice.dto.NoticeResponseDto;
import com.tbp.honeyjar.notice.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/settings")
public class NoticeController {

    private final UserMapper userMapper;
    private final NoticeService noticeService;

    public NoticeController(UserMapper userMapper, NoticeService noticeService) {
        this.userMapper = userMapper;
        this.noticeService = noticeService;
    }

    @GetMapping(value = "/notice")
    public String noticeView(Model model, Authentication authentication) {
        log.debug("# noticeView - authentication = {}", authentication);

        List<NoticeListResponseDto> noticeList = noticeService.findAllNotices();

        if (authentication != null && authentication.isAuthenticated()) {
            String kakaoId = authentication.getName();

            User user = userMapper.findByKakaoId(kakaoId);

            if (user != null) {
                if (!noticeList.isEmpty()) {
                    model.addAttribute("noticeList", noticeList);
                }
//                return "pages/settings/notice/notice";
                return "pages/notice/notice";
            } else {
                log.warn("User not found for Kakao ID: {}", kakaoId);
            }
        }

        log.warn("# No Authentication #");
        return "redirect:/login";
    }

    @GetMapping(value = "/notice/{noticeId}")
    public String noticeDetailView(
            @PathVariable Long noticeId,
            Model model,
            Authentication authentication
    ) {
        log.debug("# noticeDetailView - authentication = {}", authentication);

        NoticeResponseDto notice = noticeService.findById(noticeId);

        if (authentication != null && authentication.isAuthenticated()) {
            String kakaoId = authentication.getName();

            User user = userMapper.findByKakaoId(kakaoId);

            if (user != null) {
                if (notice != null) {
                    model.addAttribute("notice", notice);
                }
                return "pages/settings/notice/noticeDetail";
//                return "pages/notice/noticeDetail";
            } else {
                log.warn("User not found for Kakao ID: {}", kakaoId);
            }
        }

        log.warn("# No Authentication #");
        return "redirect:/login";
    }
}
