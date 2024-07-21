package com.tbp.honeyjar.settings.controller.user;

import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(value = "/settings")
public class SettingsController {

    private final UserMapper userMapper;

    public SettingsController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping
    public String settingsView(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String kakaoId = authentication.getName();
            log.debug("# String kakaoId: {}", kakaoId);

            User user = userMapper.findByKakaoId(kakaoId);
            log.debug("# User user: {}", user);

            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("name", user.getName());

//                // 프로필 이미지 URL 추가
//                String profileImageUrl = user.getProfileImageUrl(); // User 엔티티에 이 필드가 있다고 가정
//                if (profileImageUrl == null || profileImageUrl.isEmpty()) {
//                    profileImageUrl = "/assets/svg/base_profile.svg"; // 기본 이미지 경로
//                }
//                model.addAttribute("profileImageUrl", profileImageUrl);

                return "pages/settings/settings";
            } else {
                log.warn("Kakao ID not found: {}", kakaoId);
                return "redirect:/login";
            }
        } else {
            log.warn("# No Authentication #");
            return null;
        }
    }
}
