package com.tbp.honeyjar.login.config.security;

import com.tbp.honeyjar.login.common.CookieUtil;
import com.tbp.honeyjar.login.config.properties.AppProperties;
import com.tbp.honeyjar.login.config.properties.CorsProperties;
import com.tbp.honeyjar.login.oauth.entity.RoleType;
import com.tbp.honeyjar.login.oauth.exception.RestAuthenticationEntryPoint;
import com.tbp.honeyjar.login.oauth.filter.LoginPageFilter;
import com.tbp.honeyjar.login.oauth.filter.TokenAuthenticationFilter;
import com.tbp.honeyjar.login.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.tbp.honeyjar.login.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.tbp.honeyjar.login.oauth.handler.TokenAccessDeniedHandler;
import com.tbp.honeyjar.login.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.tbp.honeyjar.login.oauth.service.CustomOAuth2UserService;
import com.tbp.honeyjar.login.oauth.token.AuthTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;

import static com.tbp.honeyjar.login.common.HeaderUtil.ACCESS_TOKEN;
import static com.tbp.honeyjar.login.common.HeaderUtil.REFRESH_TOKEN;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public SecurityConfig(CorsProperties corsProperties, AppProperties appProperties, AuthTokenProvider tokenProvider, CustomOAuth2UserService oAuth2UserService, TokenAccessDeniedHandler tokenAccessDeniedHandler, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.corsProperties = corsProperties;
        this.appProperties = appProperties;
        this.tokenProvider = tokenProvider;
        this.oAuth2UserService = oAuth2UserService;
        this.tokenAccessDeniedHandler = tokenAccessDeniedHandler;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())  // CORS 설정 적용
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화 (RestAPI 이므로)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안 함
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(tokenAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                                // 정적 리소스에 대한 접근 허용
                                .requestMatchers("/assets/**", "/css/**", "/js/**", "/images/**", "/favicon.*").permitAll()

                                // 인증이 필요 없는 public 엔드포인트
                                .requestMatchers("/login", "/admin/login").permitAll()
                                .requestMatchers("/oauth2/authorization/**", "/login/oauth2/code/**").permitAll()

                                // 관리자 전용 엔드포인트
                                .requestMatchers("/admin").hasAuthority(RoleType.ADMIN.getCode())
                                .requestMatchers("/admin/**").hasAuthority(RoleType.ADMIN.getCode())
//                                .requestMatchers("/admin/**").permitAll()

                                // 인증이 필요한 사용자 엔드포인트
                                .requestMatchers("/home", "/post/**", "/mypage/**", "/follow/**", "/settings/**").authenticated()

                                // 그 외 모든 요청은 인증 필요
//                                .anyRequest().authenticated()
//                        // 개발 중에 임시로 모든 엔드포인트를 허용
                                .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/oauth2/authorization") // OAuth2 로그인 시작 URI
                                .authorizationRequestRepository(authorizationRequestRepository()) // 쿠키 기반 인증 요청 저장소
                        )
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/login/oauth2/code/*") // OAuth2 로그인 후 리디렉션 URI
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService) // OAuth2 사용자 정보 처리 서비스
                        )
                        .successHandler(oAuth2AuthenticationSuccessHandler()) // OAuth2 로그인 성공 핸들러
                        .failureHandler(oAuth2AuthenticationFailureHandler()) // OAuth2 로그인 실패 핸들러
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(this::customLogoutHandler)
                        .logoutSuccessHandler(this::customLogoutSuccessHandler)
                        .deleteCookies(ACCESS_TOKEN, REFRESH_TOKEN)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )
                .addFilterBefore(loginPageFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 토큰 인증 필터 추가

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
     * Cors 설정
     * */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        if (corsProperties.getAllowedOrigins() != null && !corsProperties.getAllowedOrigins().isEmpty()) {
            for (String allowedOrigin : corsProperties.getAllowedOrigins()) {
                config.addAllowedOriginPattern(allowedOrigin);
            }
        }

        config.setAllowedMethods(corsProperties.getAllowedMethods());
        config.setAllowedHeaders(corsProperties.getAllowedHeaders());
        config.setAllowCredentials(true);
        config.setMaxAge(corsProperties.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /*
     * 토큰 필터 설정
     * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider, appProperties);
    }

    /*
     * 쿠키 기반 인가 Repository
     * 인가 응답을 연계 하고 검증할 때 사용.
     * */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /*
     * Oauth 인증 성공 핸들러
     * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                authorizationRequestRepository()
        );
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(authorizationRequestRepository());
    }

    @Bean
    public LoginPageFilter loginPageFilter() {
        return new LoginPageFilter();
    }

    private void customLogoutHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        // 토큰 삭제 등의 커스텀 로그아웃 로직
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
    }

    private void customLogoutSuccessHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(RoleType.ADMIN.getCode()));

        String redirectUrl = isAdmin ? "/admin/login" : "/login";
        response.setContentType("application/json");
        response.getWriter().write("{\"status\":200,\"message\":\"Logout successful\",\"data\":{\"redirectUrl\":\"" + redirectUrl + "\"}}");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
