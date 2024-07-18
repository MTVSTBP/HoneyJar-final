package com.tbp.honeyjar.login.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbp.honeyjar.login.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        log.error("Responding with access denied error. Message := {}", accessDeniedException.getMessage());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        ApiResponse<?> apiResponse = new ApiResponse<>(HttpStatusCode.valueOf(401), accessDeniedException.getMessage());
        response.getWriter().print(objectMapper.writeValueAsString(apiResponse));
    }
}