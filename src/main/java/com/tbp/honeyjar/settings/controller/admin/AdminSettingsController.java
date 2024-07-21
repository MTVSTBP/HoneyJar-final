package com.tbp.honeyjar.settings.controller.admin;

import com.tbp.honeyjar.login.common.CookieUtil;
import com.tbp.honeyjar.login.entity.admin.Admin;
import com.tbp.honeyjar.login.mapper.admin.AdminMapper;
import com.tbp.honeyjar.login.oauth.token.AuthToken;
import com.tbp.honeyjar.login.oauth.token.AuthTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.tbp.honeyjar.login.common.HeaderUtil.ACCESS_TOKEN;

@Slf4j
@Controller
@RequestMapping(value = "/admin")
public class AdminSettingsController {

    private final AuthTokenProvider authTokenProvider;
    private final AdminMapper adminMapper;

    public AdminSettingsController(AuthTokenProvider authTokenProvider, AdminMapper adminMapper) {
        this.authTokenProvider = authTokenProvider;
        this.adminMapper = adminMapper;
    }

    @GetMapping(value = "/settings")
    public String adminSettingsView(HttpServletRequest request, Model model) {
        String token = extractToken(request);  // 토큰 추출 메서드

        if (token != null && !token.isEmpty()) {
            try {
                AuthToken authToken = authTokenProvider.convertAuthToken(token);
                if (authToken.validate()) {
                    Claims claims = authToken.getTokenClaims();
                    String adminId = claims.getSubject();
                    Admin admin = adminMapper.findByAdminId(Long.parseLong(adminId));
                    if (admin != null) {
                        model.addAttribute("admin", admin);
                        log.debug("# model: {}", model);
                        return "pages/settings/adminSettings";
                    }
                }
            } catch (Exception e) {
                log.error("Error validating token", e);
            }
        }
        return "redirect:/admin/login";
    }

    private String extractToken(HttpServletRequest request) {
        return CookieUtil.getCookie(request, ACCESS_TOKEN).map(Cookie::getValue).orElse(null);
    }
}
