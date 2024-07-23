package com.tbp.honeyjar.login.oauth.filter;

import com.tbp.honeyjar.login.common.HeaderUtil;
import com.tbp.honeyjar.login.oauth.token.AuthToken;
import com.tbp.honeyjar.login.oauth.token.AuthTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.tbp.honeyjar.login.common.HeaderUtil.ACCESS_TOKEN;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter_bak extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String tokenStr = HeaderUtil.getAccessToken(request);
        log.debug("Token from header: {}", tokenStr);

        if (tokenStr == null) {
            // 쿠키에서 토큰 가져오기 추가
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(ACCESS_TOKEN)) {
                        tokenStr = cookie.getValue();
                        log.debug("Token from cookie: {}", tokenStr);
                        break;
                    }
                }
            }
        }

        if (tokenStr != null) {
            AuthToken token = tokenProvider.convertAuthToken(tokenStr);

            if (token.validate()) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.warn("Invalid Access Token");
            }
        } else {
            log.warn("Access Token not found");
        }

        filterChain.doFilter(request, response);
    }
}
