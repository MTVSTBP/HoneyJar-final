package com.tbp.honeyjar.login.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

    @GetMapping(value = "/login")
    public String loginView() {
        return "pages/login/login"; // 로그인 페이지 뷰 반환
    }
}
