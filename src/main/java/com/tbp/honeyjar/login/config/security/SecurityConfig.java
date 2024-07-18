package com.tbp.honeyjar.login.config.security;

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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
//                .formLogin(form -> form
//                        .loginPage("/login") // 로그인 페이지 URL 설정
//                        // TODO: 관리자 로그인은 어떻게 해야 할까?
//                        .permitAll()
//                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(tokenAccessDeniedHandler)
                )
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint(new RestAuthenticationEntryPoint() {
//                            @Override
//                            public void commence(
//                                    HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    AuthenticationException authException
//                            ) throws IOException {
//                                // TODO: 관리자 로그인은 어떻게 해야 할까?
//                                response.sendRedirect("/login");  // 인증되지 않은 사용자를 로그인 페이지로 리다이렉트
//                            }
//                        })
//                        .accessDeniedHandler(tokenAccessDeniedHandler)
//                )
                .authorizeHttpRequests(auth -> auth
                                // !!! 순서가 중요함 !!!
//                        .requestMatchers("/**").permitAll() // TODO: 모든 엔드포인트 허용 (개발 중 임시 설정)
                                .requestMatchers("/assets/**", "/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.*", "/*/icon-*").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/oauth2/authorization/**", "/login/oauth2/code/**").permitAll()
                                .requestMatchers("/post/**").authenticated()  // '/post'로 시작하는 URL은 인증 필요(사용자 로그인이 완료 후 토큰(액세스/리프레시)이 발급된 이후에 접속 가능)
                                .requestMatchers("/admin").hasAuthority(RoleType.ADMIN.getCode())
                                .requestMatchers("/admin/**").hasAuthority(RoleType.ADMIN.getCode())
                                .requestMatchers("/admin/login").permitAll()
//                        .anyRequest().permitAll() // TODO: 나머지 요청은 모두 허용 (개발 중 임시 설정)
                                .anyRequest().authenticated() // 이게 맞는 설정임!
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
}
