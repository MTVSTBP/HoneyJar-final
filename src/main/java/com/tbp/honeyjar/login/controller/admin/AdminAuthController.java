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
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.tbp.honeyjar.login.common.ApiResponse.*;
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> adminLogin(
            @RequestBody AdminAuthenticationDTO adminAuthenticationDTO,
            HttpServletResponse response
    ) {
        log.debug("Attempting admin login for email: {}", adminAuthenticationDTO.getEmail());

        Admin admin = adminMapper.findByEmail(adminAuthenticationDTO.getEmail());

        if (admin != null) {
            log.debug("Admin found: {}", admin);

            if (passwordEncoder.matches(adminAuthenticationDTO.getPassword(), admin.getPassword())) {
                log.debug("Password matched for admin: {}", admin.getEmail());

                if (admin.getAdminId() == null) {
                    log.error("Admin found but adminId is null for email: {}", admin.getEmail());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ApiResponse<>(ApiResponse.INTERNAL_SERVER_ERROR_CODE, "Internal server error", null));
                }

                Date now = new Date();
                AuthToken accessToken = tokenProvider.createAuthToken(
                        admin.getAdminId().toString(),
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

                Map<String, String> resultMap = new HashMap<>();
                resultMap.put(TOKEN_NAME, accessToken.getToken());
                resultMap.put("redirectUrl", "/admin");

                log.info("Admin login successful for email: {}", admin.getEmail());
                return ResponseEntity.ok(new ApiResponse<>(SUCCESS_CODE, "Login Successful", resultMap));
            } else {
                log.debug("Password does not match for admin: {}", admin.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(UNAUTHORIZED_CODE, "아이디 또는 비밀번호가 잘못되었습니다.", null));
            }
        } else {
            log.debug("No admin found for email: {}", adminAuthenticationDTO.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(UNAUTHORIZED_CODE, "아이디 또는 비밀번호가 잘못되었습니다.", null));
        }
    }
}
