package com.tbp.honeyjar.login.oauth.handler;

import com.tbp.honeyjar.login.common.CookieUtil;
import com.tbp.honeyjar.login.config.properties.AppProperties;
import com.tbp.honeyjar.login.oauth.entity.RoleType;
import com.tbp.honeyjar.login.oauth.entity.user.UserPrincipal;
import com.tbp.honeyjar.login.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.tbp.honeyjar.login.oauth.token.AuthToken;
import com.tbp.honeyjar.login.oauth.token.AuthTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Optional;

import static com.tbp.honeyjar.login.common.HeaderUtil.ACCESS_TOKEN;
import static com.tbp.honeyjar.login.common.HeaderUtil.REFRESH_TOKEN;
import static com.tbp.honeyjar.login.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        // 토큰 생성 로직
        String userId = getUserIdFromAuthentication(authentication);
        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                RoleType.USER.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        // 리프레시 토큰 생성
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // 쿠키에 토큰 저장
        int accessTokenMaxAge = (int) appProperties.getAuth().getTokenExpiry() / 1000;
        CookieUtil.addCookie(response, ACCESS_TOKEN, accessToken.getToken(), accessTokenMaxAge);

        int refreshTokenMaxAge = (int) refreshTokenExpiry / 1000;
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), refreshTokenMaxAge);

        // SecurityContext 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
//        String targetUrl = redirectUri.orElse("/home");
        log.info("Before Redirecting to: {}", targetUrl);

        // 사용자 역할에 따라 targetUrl 결정
        if (hasAdminRole(authentication)) {
            targetUrl = "/admin";
        } else {
            targetUrl = "/home";
        }
        log.info("After Redirecting to: {}", targetUrl);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private String getUserIdFromAuthentication(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
            return userPrincipal.getUserId().toString();
        }
        throw new IllegalArgumentException("Unexpected authentication principal");
    }

    private boolean hasAdminRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(RoleType.ADMIN.getCode()));
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort();
                });
    }
}
