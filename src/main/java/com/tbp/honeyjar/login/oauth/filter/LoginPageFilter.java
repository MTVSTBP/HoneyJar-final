package com.tbp.honeyjar.login.oauth.filter;

import com.tbp.honeyjar.login.common.CookieUtil;
import com.tbp.honeyjar.login.oauth.entity.RoleType;
import com.tbp.honeyjar.login.oauth.token.AuthToken;
import com.tbp.honeyjar.login.oauth.token.AuthTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.tbp.honeyjar.login.common.HeaderUtil.ACCESS_TOKEN;
import static com.tbp.honeyjar.login.common.HeaderUtil.REFRESH_TOKEN;

@Slf4j
public class LoginPageFilter extends OncePerRequestFilter {

    private final AuthTokenProvider authTokenProvider;

    public LoginPageFilter(AuthTokenProvider authTokenProvider) {
        this.authTokenProvider = authTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (isLoginPage(request) && isAuthenticated(request)) {
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

    private boolean isAuthenticated(HttpServletRequest request) {
        String accessToken = CookieUtil.getCookie(request, ACCESS_TOKEN)
                .map(Cookie::getValue)
                .orElse(null);
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse(null);

        if (accessToken != null) {
            AuthToken authToken = authTokenProvider.convertAuthToken(accessToken);
            if (authToken.validate()) {
                return true;
            }
        }

        if (refreshToken != null) {
            AuthToken authRefreshToken = authTokenProvider.convertAuthToken(refreshToken);
            if (authRefreshToken.validate()) {
                return true;
            }
        }

        return false;
    }

    private String determineHomeUrl(HttpServletRequest request) {
        log.debug("# request.getRequestURI(): {}", request.getRequestURI());
        if (request.getRequestURI().startsWith("/admin")) {
            return "/admin";  // 관리자 홈페이지
        }
        return "/";  // 일반 사용자 홈페이지
    }
}
