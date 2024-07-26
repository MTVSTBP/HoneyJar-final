package com.tbp.honeyjar.login.controller.user;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

    @GetMapping(value = "/login")
    public String loginView(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/home";  // 또는 다른 적절한 페이지로 리다이렉트
        }

        return "pages/login/login";
    }
}
