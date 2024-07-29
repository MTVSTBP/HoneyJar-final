package com.tbp.honeyjar.settings.controller.user;

import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import com.tbp.honeyjar.login.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
    public String settingsView(Model model, Authentication authentication) {
        log.debug("authentication = {}", authentication);

        if (authentication != null && authentication.isAuthenticated()) {
            String kakaoId = authentication.getName();
            log.info("Settings page accessed - Kakao ID: {}", kakaoId);

            User user = userMapper.findByKakaoId(kakaoId);

            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("name", user.getName());
                return "pages/settings/settings";
            } else {
                log.warn("User not found for Kakao ID: {}", kakaoId);
            }
        }

        log.warn("# No Authentication #");
        return "redirect:/login";
    }
}
