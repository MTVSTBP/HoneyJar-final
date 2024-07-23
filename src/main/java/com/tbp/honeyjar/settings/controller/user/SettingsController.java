package com.tbp.honeyjar.settings.controller.user;

import com.tbp.honeyjar.login.common.ApiResponse;
import com.tbp.honeyjar.login.common.CookieUtil;
import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import com.tbp.honeyjar.login.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.tbp.honeyjar.login.common.ApiResponse.SUCCESS_CODE;
import static com.tbp.honeyjar.login.common.HeaderUtil.ACCESS_TOKEN;
import static com.tbp.honeyjar.login.common.HeaderUtil.REFRESH_TOKEN;

@Slf4j
@Controller
@RequestMapping(value = "/settings")
public class SettingsController {

    private final UserMapper userMapper;
    private final UserService userService;

    public SettingsController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
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

    @GetMapping(value = "/leave")
    public String leaveView(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String kakaoId = authentication.getName();
            log.debug("# String kakaoId: {}", kakaoId);

            User user = userMapper.findByKakaoId(kakaoId);
            log.debug("# User user: {}", user);

            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("name", user.getName());

                return "pages/settings/leave";
            } else {
                log.warn("Kakao ID not found: {}", kakaoId);
                return "redirect:/login";
            }
        } else {
            log.warn("# No Authentication #");
            return null;
        }
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leaveUser(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String kakaoId = authentication.getName();
            userService.deleteUser(kakaoId);

            // 로그아웃 처리
            new SecurityContextLogoutHandler().logout(request, response, authentication);

            // 토큰 삭제
            CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);

            return ResponseEntity.ok().body(new ApiResponse<>(SUCCESS_CODE, "User leave successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(SUCCESS_CODE, "User not authenticated", null));
        }
    }
}
