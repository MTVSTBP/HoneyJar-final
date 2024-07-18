package com.tbp.honeyjar.login.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserLoginController {

    @GetMapping(value = "/login")
    public String loginView() {
        return "pages/login/login"; // 로그인 페이지 뷰 반환
    }
}
