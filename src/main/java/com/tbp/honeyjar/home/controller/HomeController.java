package com.tbp.honeyjar.home.controller;

import com.tbp.honeyjar.login.oauth.entity.RoleType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/home", "/admin"})
    public String home(Model model, Authentication authentication) {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(RoleType.ADMIN.getCode()));

        model.addAttribute("isAdmin", isAdmin);

        return "pages/home/home";
    }
}
