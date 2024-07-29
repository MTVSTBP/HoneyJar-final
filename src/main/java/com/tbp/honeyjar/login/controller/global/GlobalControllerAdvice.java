package com.tbp.honeyjar.login.controller.global;

import com.tbp.honeyjar.login.oauth.entity.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addAttributes(Model model, Authentication authentication) {
        boolean isAdmin = false;
        if (authentication != null && authentication.isAuthenticated()) {
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals(RoleType.ADMIN.getCode()));
        }
        model.addAttribute("isAdmin", isAdmin);
        log.debug("isAdmin: {}", isAdmin); // 디버깅을 위한 로그 추가
    }
}
