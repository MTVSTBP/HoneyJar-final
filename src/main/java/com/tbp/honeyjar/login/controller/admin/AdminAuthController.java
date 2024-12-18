package com.tbp.honeyjar.login.controller.admin;

import com.tbp.honeyjar.login.DTO.AdminAuthenticationDTO;
import com.tbp.honeyjar.login.common.ApiResponse;
import com.tbp.honeyjar.login.common.CookieUtil;
import com.tbp.honeyjar.login.config.properties.AppProperties;
import com.tbp.honeyjar.login.controller.auth.AbstractAuthController;
import com.tbp.honeyjar.login.entity.admin.Admin;
import com.tbp.honeyjar.login.mapper.admin.AdminMapper;
import com.tbp.honeyjar.login.oauth.entity.RoleType;
import com.tbp.honeyjar.login.oauth.token.AuthToken;
import com.tbp.honeyjar.login.oauth.token.AuthTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.tbp.honeyjar.login.common.HeaderUtil.*;

@Slf4j
@RestController
@RequestMapping(value = "/admin")
public class AdminAuthController extends AbstractAuthController {

    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminAuthController(AppProperties appProperties, AuthTokenProvider tokenProvider, AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        super(appProperties, tokenProvider);
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<String>> adminLogin(
            @RequestBody AdminAuthenticationDTO adminAuthenticationDTO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        log.debug("Attempting admin login for email: {}", adminAuthenticationDTO.getEmail());

        Admin admin = adminMapper.findByEmail(adminAuthenticationDTO.getEmail());

        if (admin != null && passwordEncoder.matches(adminAuthenticationDTO.getPassword(), admin.getPassword())) {
            // 로그인 성공 시, 액세스 토큰 및 리프레시 토큰 생성 및 반환
            Date now = new Date();
            AuthToken accessToken = tokenProvider.createAuthToken(
                    admin.getAdminId().toString(), // 관리자 ID를 이용하여 토큰 생성
                    RoleType.ADMIN.getCode(),
                    new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
            );

            log.info("Created access token for admin: {}", accessToken.getToken());

            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
            AuthToken refreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            log.debug("Created refresh token for admin");

            int accessTokenMaxAge = (int) appProperties.getAuth().getTokenExpiry() / 1000;
            CookieUtil.addCookie(response, ACCESS_TOKEN, accessToken.getToken(), accessTokenMaxAge);

            int refreshTokenMaxAge = (int) refreshTokenExpiry / 1000;
            CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), refreshTokenMaxAge);

            log.debug("Added access and refresh tokens to cookies");

            // 세션에 관리자 상태 저장
            request.getSession().setAttribute("isAdmin", true);

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put(TOKEN_NAME, accessToken.getToken());
            resultMap.put("redirectUrl", "/admin/settings");

            return ResponseEntity.ok(new ApiResponse<>(ApiResponse.SUCCESS_CODE, "Login Successful", resultMap));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(ApiResponse.UNAUTHORIZED_CODE, "아이디 또는 비밀번호가 잘못되었습니다.", null));
        }
    }
}
