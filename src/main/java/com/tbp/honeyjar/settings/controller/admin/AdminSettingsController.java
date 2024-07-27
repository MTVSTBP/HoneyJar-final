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

    private final AdminMapper adminMapper;
    private final AuthTokenProvider authTokenProvider;

    public AdminSettingsController(AuthTokenProvider authTokenProvider, AdminMapper adminMapper) {
        this.authTokenProvider = authTokenProvider;
        this.adminMapper = adminMapper;
    }

    @GetMapping(value = "/settings")
    public String adminSettingsView(HttpServletRequest request, Model model) {
        String token = extractToken(request);
        log.debug("Extracted token: {}", token);

        if (token != null && !token.isEmpty()) {
            try {
                AuthToken authToken = authTokenProvider.convertAuthToken(token);
                if (authToken.validate()) {
                    Claims claims = authToken.getTokenClaims();
                    String adminId = claims.getSubject();
                    log.debug("Extracted adminId from token: {}", adminId);

                    if (adminId != null && !adminId.equals("null") && !adminId.isEmpty()) {
                        try {
                            Long adminIdLong = Long.parseLong(adminId);
                            Admin admin = adminMapper.findByAdminId(adminIdLong);
                            if (admin != null) {
                                model.addAttribute("admin", admin);
                                log.debug("Admin found: {}", admin);
                                return "pages/settings/adminSettings";
                            } else {
                                log.warn("No admin found for id: {}", adminIdLong);
                            }
                        } catch (NumberFormatException e) {
                            log.error("Error parsing adminId: {}", adminId, e);
                        }
                    } else {
                        log.warn("Invalid adminId in token: {}", adminId);
                    }
                } else {
                    log.warn("Invalid token");
                }
            } catch (Exception e) {
                log.error("Error processing token", e);
            }
        } else {
            log.warn("No token found");
        }

        return "redirect:/admin/login";
    }

    private String extractToken(HttpServletRequest request) {
        return CookieUtil.getCookie(request, ACCESS_TOKEN).map(Cookie::getValue).orElse(null);
    }
}
