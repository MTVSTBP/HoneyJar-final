package com.tbp.honeyjar.login.oauth.handler;

import com.tbp.honeyjar.login.common.CookieUtil;
import com.tbp.honeyjar.login.config.properties.AppProperties;
import com.tbp.honeyjar.login.oauth.entity.ProviderType;
import com.tbp.honeyjar.login.oauth.entity.RoleType;
import com.tbp.honeyjar.login.oauth.info.OAuth2UserInfo;
import com.tbp.honeyjar.login.oauth.info.OAuth2UserInfoFactory;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
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

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        ProviderType providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

        OidcUser oidcUser = ((OidcUser) authentication.getPrincipal());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oidcUser.getAttributes());

        String kakaoId = "Kakao_" + userInfo.getId();
        log.debug("kakaoId: {}", kakaoId);

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                kakaoId,
                RoleType.USER.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );
        log.debug("AuthToken accessToken: {}", accessToken);

        // 리프레시 토큰 설정
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        log.debug("refreshTokenExpiry: {}", refreshTokenExpiry);

        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );
        log.debug("AuthToken refreshToken: {}", refreshToken);

        // 액세스 토큰 쿠키 설정
        int accessTokenCookieMaxAge = (int) appProperties.getAuth().getTokenExpiry() / 1000;
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
        CookieUtil.addCookie(response, ACCESS_TOKEN, accessToken.getToken(), accessTokenCookieMaxAge);

        // 리프레시 토큰 쿠키 설정
        int refreshTokenCookieMaxAge  = (int) refreshTokenExpiry / 1000;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), refreshTokenCookieMaxAge);

        return targetUrl;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
        if (authorities == null) {
            return false;
        }

        for (GrantedAuthority grantedAuthority : authorities) {
            if (authority.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
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
