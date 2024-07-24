package com.tbp.honeyjar.home.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/home", "/admin"})
    public String home(Model model, Authentication authentication) {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);

        // 사용자 타입에 따른 추가 데이터 설정
        if (isAdmin) {
            // 관리자를 위한 추가 데이터 설정
            model.addAttribute("adminData", "Some admin specific data");
        } else {
            // 일반 사용자를 위한 추가 데이터 설정
            model.addAttribute("userData", "Some user specific data");
        }

        return "pages/home/home";
    }
}
