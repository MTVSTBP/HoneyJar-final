package com.tbp.honeyjar.login.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminLoginController {

    @GetMapping(value = "/admin/login")
    public String adminLoginView() {
        return "pages/login/adminLogin"; // 관리자 로그인 페이지 뷰 반환
    }
}
