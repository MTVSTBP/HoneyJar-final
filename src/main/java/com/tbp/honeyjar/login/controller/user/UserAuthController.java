package com.tbp.honeyjar.login.controller.user;

import com.tbp.honeyjar.login.common.ApiResponse;
import com.tbp.honeyjar.login.common.CookieUtil;
import com.tbp.honeyjar.login.config.properties.AppProperties;
import com.tbp.honeyjar.login.controller.auth.AbstractAuthController;
import com.tbp.honeyjar.login.oauth.entity.RoleType;
import com.tbp.honeyjar.login.oauth.entity.user.UserPrincipal;
import com.tbp.honeyjar.login.oauth.token.AuthToken;
import com.tbp.honeyjar.login.oauth.token.AuthTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.tbp.honeyjar.login.common.ApiResponse.SUCCESS_MESSAGE;
import static com.tbp.honeyjar.login.common.HeaderUtil.*;

@Slf4j
@RestController
public class UserAuthController extends AbstractAuthController {

    public UserAuthController(
            AppProperties appProperties,
            AuthTokenProvider tokenProvider
    ) {
        super(appProperties, tokenProvider); // 부모 클래스의 생성자 호출
    }

    @PostMapping("/login") // 사용자 로그인 엔드포인트
    public ResponseEntity<ApiResponse<String>> login(
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(ApiResponse.UNAUTHORIZED_CODE, "인증에 실패했습니다.", null));
        }

        // 로그인 성공 시, 액세스 토큰 및 리프레시 토큰 생성 및 반환
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getUserId();

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId.toString(),
                RoleType.USER.getCode(),
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
        // TODO: 지금은 리다이렉트 되는 주소가 "localhost:8080"
        //  -> 이를 "localhost:8080/home"으로 되게끔 수정
        //  -> 별도의 home.html 파일을 생성 후 수정하면 될 듯
        resultMap.put("redirectUrl", "/home");  // 일반 사용자는 홈페이지로 리다이렉트
//        resultMap.put("redirectUrl", "");  // 일반 사용자는 홈페이지로 리다이렉트

        return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS_CODE, "Login successful", resultMap));
    }
}
