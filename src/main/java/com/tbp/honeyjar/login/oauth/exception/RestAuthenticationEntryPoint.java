package com.tbp.honeyjar.login.oauth.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        // API 요청인 경우 401 상태 코드와 JSON 응답 반환
        if (isApiRequest(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
        } else {
            // 웹 페이지 요청인 경우 로그인 페이지로 리다이렉트
            response.sendRedirect("/login");
        }
    }

    private boolean isApiRequest(HttpServletRequest request) {
        // API 요청을 구분하는 로직 (예: Accept 헤더가 application/json인 경우)
        String accept = request.getHeader("Accept");
        return accept != null && accept.contains("application/json");
    }
}
