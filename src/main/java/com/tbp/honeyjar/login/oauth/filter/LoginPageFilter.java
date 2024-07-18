package com.tbp.honeyjar.login.oauth.filter;

import com.tbp.honeyjar.login.oauth.entity.RoleType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class LoginPageFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (isLoginPage(request) && isAuthenticated()) {
            String homeUrl = determineHomeUrl(request);
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
        if (request.getRequestURI().startsWith("/admin")) {
            return "/admin";  // 관리자 홈페이지
        }
        return "/";  // 일반 사용자 홈페이지
    }

//    private boolean isAdminLoginPage(HttpServletRequest request) {
//        return request.getRequestURI().equals("/admin/login");
//    }

//    private boolean isAdmin() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals(RoleType.ADMIN.getCode()));
//    }
}
