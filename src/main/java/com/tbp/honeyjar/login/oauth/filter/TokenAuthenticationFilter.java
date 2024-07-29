package com.tbp.honeyjar.login.oauth.filter;

import com.tbp.honeyjar.login.common.CookieUtil;
import com.tbp.honeyjar.login.config.properties.AppProperties;
import com.tbp.honeyjar.login.oauth.entity.RoleType;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

import static com.tbp.honeyjar.login.common.HeaderUtil.ACCESS_TOKEN;
import static com.tbp.honeyjar.login.common.HeaderUtil.REFRESH_TOKEN;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String accessToken = CookieUtil.getCookie(request, ACCESS_TOKEN)
                .map(Cookie::getValue)
                .orElse(null);

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (authToken.validate()) {
            Authentication authentication = tokenProvider.getAuthentication(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // 액세스 토큰이 만료된 경우 리프레시 토큰으로 새로운 액세스 토큰 발급
            String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                    .map(Cookie::getValue)
                    .orElse(null);
            if (refreshToken != null) {
                AuthToken refresh = tokenProvider.convertAuthToken(refreshToken);
                if (refresh.validate()) {
                    // 새로운 액세스 토큰 발급 및 쿠키 설정
                    renewAccessToken(request, response, refresh);
                } else {
                    // 리프레시 토큰도 만료된 경우
                    CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
                    CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);

                    // 토큰이 유효하지 않은 경우 처리
                    SecurityContextHolder.clearContext();
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private void renewAccessToken(HttpServletRequest request, HttpServletResponse response, AuthToken refreshToken) {
        // 리프레시 토큰에서 사용자 정보 추출
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);

        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(RoleType.USER.getCode());

        // 새로운 액세스 토큰 생성
        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                authentication.getName(),
                role,
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        // 새로운 액세스 토큰을 쿠키에 설정
        int accessTokenMaxAge = (int) appProperties.getAuth().getTokenExpiry() / 1000;
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
        CookieUtil.addCookie(response, ACCESS_TOKEN, newAccessToken.getToken(), accessTokenMaxAge);

        // SecurityContext 업데이트
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Access token renewed for user: {}", authentication.getName());
    }
}
