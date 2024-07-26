package com.tbp.honeyjar.login.controller.auth;

import com.tbp.honeyjar.login.common.ApiResponse;
import com.tbp.honeyjar.login.common.CookieUtil;
import com.tbp.honeyjar.login.common.HeaderUtil;
import com.tbp.honeyjar.login.config.properties.AppProperties;
import com.tbp.honeyjar.login.oauth.token.AuthToken;
import com.tbp.honeyjar.login.oauth.token.AuthTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

import static com.tbp.honeyjar.login.common.HeaderUtil.*;

@Slf4j
@Getter
public abstract class AbstractAuthController {

    protected final AppProperties appProperties;
    protected final AuthTokenProvider tokenProvider;

    protected AbstractAuthController(AppProperties appProperties, AuthTokenProvider tokenProvider) { // protected 생성자 추가
        this.appProperties = appProperties;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) throws ServletException {
        request.getSession().removeAttribute("isAdmin");
        // Spring Security의 로그아웃 처리 위임
        request.logout();
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refreshToken(
            HttpServletRequest request
    ) {
        // access token 확인
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (!authToken.validate()) {
            return ApiResponse.invalidAccessToken();
        }

        // expired access token 인지 확인
        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            return ApiResponse.notExpiredTokenYet();
        }

        String userId = claims.getSubject();
        String role = claims.get("role", String.class);

        // refresh token
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse(null);
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        if (!authRefreshToken.validate()) {
            return ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                userId,
                role,
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        return ApiResponse.success(TOKEN_NAME, newAccessToken.getToken());
    }
}
