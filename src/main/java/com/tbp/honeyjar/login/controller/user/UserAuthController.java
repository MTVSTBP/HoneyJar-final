package com.tbp.honeyjar.login.controller.user;

import com.tbp.honeyjar.login.common.ApiResponse;
import com.tbp.honeyjar.login.common.CookieUtil;
import com.tbp.honeyjar.login.config.properties.AppProperties;
import com.tbp.honeyjar.login.controller.auth.AbstractAuthController;
import com.tbp.honeyjar.login.oauth.entity.RoleType;
import com.tbp.honeyjar.login.oauth.entity.user.UserPrincipal;
import com.tbp.honeyjar.login.oauth.token.AuthToken;
import com.tbp.honeyjar.login.oauth.token.AuthTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.tbp.honeyjar.login.common.ApiResponse.SUCCESS_CODE;
import static com.tbp.honeyjar.login.common.ApiResponse.UNAUTHORIZED_CODE;
import static com.tbp.honeyjar.login.common.HeaderUtil.*;

@Slf4j
@RestController
public class UserAuthController extends AbstractAuthController {

    public UserAuthController(
            AppProperties appProperties,
            AuthTokenProvider tokenProvider
    ) {
        super(appProperties, tokenProvider);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(UNAUTHORIZED_CODE, "인증에 실패했습니다.", null));
        }

        Object principal = authentication.getPrincipal();
        String userId;
        String userRole;

        if (principal instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) principal;
            userId = userPrincipal.getUserId().toString();
            userRole = RoleType.USER.getCode();
        } else if (principal instanceof String) {
            userId = (String) principal;
            userRole = RoleType.USER.getCode();
        } else {
            log.error("Unexpected principal type: {}", principal.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(ApiResponse.INTERNAL_SERVER_ERROR_CODE, "내부 서버 오류", null));
        }

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                userRole,
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // 액세스 토큰 쿠키 설정
        int accessTokenMaxAge = (int) appProperties.getAuth().getTokenExpiry() / 1000;
        CookieUtil.addCookie(response, ACCESS_TOKEN, accessToken.getToken(), accessTokenMaxAge);

        // 리프레시 토큰 쿠키 설정
        int refreshTokenMaxAge = (int) refreshTokenExpiry / 1000;
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), refreshTokenMaxAge);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(TOKEN_NAME, accessToken.getToken());
        resultMap.put("redirectUrl", "/");

        return ResponseEntity.ok(new ApiResponse<>(SUCCESS_CODE, "Login successful", resultMap));
    }
}