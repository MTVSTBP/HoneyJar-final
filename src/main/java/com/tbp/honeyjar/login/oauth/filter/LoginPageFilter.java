package com.tbp.honeyjar.login.oauth.filter;

import com.tbp.honeyjar.login.oauth.entity.RoleType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class LoginPageFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (isLoginPage(request) && isAuthenticated()) {
            String homeUrl = determineHomeUrl(request);
            log.debug("Authenticated user attempting to access login page. Redirecting to: {}", homeUrl);
            response.sendRedirect(homeUrl);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isLoginPage(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.equals("/login") || requestURI.equals("/admin/login");
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken);
    }

    private String determineHomeUrl(HttpServletRequest request) {
        log.debug("# request.getRequestURI(): {}", request.getRequestURI());
        if (request.getRequestURI().startsWith("/admin")) {
            if (isAdmin()) {
                return "/admin";  // 관리자 홈페이지
            } else {
                log.warn("Non-admin user attempting to access admin login page");
                return "/home";  // 일반 사용자 홈페이지로 리다이렉트
            }
        }
        return "/home";  // 일반 사용자 홈페이지
    }

    private boolean isAdmin() {
        log.debug("# isAdmin() 메서드 진입...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(RoleType.ADMIN.getCode()));
    }
}
