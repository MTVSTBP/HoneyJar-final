package com.tbp.honeyjar.login.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ApiResponse<T> {

    // HttpStatusCode 상수 정의
    public static final HttpStatusCode SUCCESS_CODE = HttpStatusCode.valueOf(200);
    public static final HttpStatusCode CREATED_CODE = HttpStatusCode.valueOf(201);
    public static final HttpStatusCode BAD_REQUEST_CODE = HttpStatusCode.valueOf(400);
    public static final HttpStatusCode UNAUTHORIZED_CODE = HttpStatusCode.valueOf(401);
    public static final HttpStatusCode NOT_FOUND_CODE = HttpStatusCode.valueOf(404);
    public static final HttpStatusCode CONFLICT_CODE = HttpStatusCode.valueOf(409);
    public static final HttpStatusCode INTERNAL_SERVER_ERROR_CODE = HttpStatusCode.valueOf(500);

    // 메시지 상수 정의
    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final String BAD_REQUEST_MESSAGE = "BAD REQUEST";
    public static final String UNAUTHORIZED_MESSAGE = "UNAUTHORIZED";
    public static final String NOT_FOUND_MESSAGE = "NOT FOUND";
    public static final String CONFLICT_MESSAGE = "CONFLICT";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "INTERNAL SERVER ERROR";
    public static final String INVALID_ACCESS_TOKEN = "INVALID ACCESS TOKEN.";
    public static final String INVALID_REFRESH_TOKEN = "INVALID REFRESH TOKEN.";
    public static final String NOT_EXPIRED_TOKEN_YET = "NOT EXPIRED TOKEN YET.";


    private final int status;
    private final String message;
    private final Map<String, T> data;

    // 생성자: HttpStatusCode를 받도록 수정
    public ApiResponse(HttpStatusCode statusCode, String message, Map<String, T> data) {
        this.status = statusCode.value();
        this.message = message;
        this.data = data;
    }

    public ApiResponse(HttpStatusCode statusCode, String message) {
        this(statusCode, message, null); // data를 null로 설정하는 생성자 호출
    }

    // 생성 성공 응답 (201 Created)
    public static <T> ResponseEntity<ApiResponse<T>> created(String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);

        return new ResponseEntity<>(new ApiResponse<>(CREATED_CODE, SUCCESS_MESSAGE, map), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);

        return ResponseEntity.ok(new ApiResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, map));
    }

    // 실패 응답
    public static <T> ResponseEntity<ApiResponse<T>> fail(HttpStatusCode statusCode, String message) {
        return new ResponseEntity<>(new ApiResponse<>(statusCode, message, null), statusCode);
    }

    public static <T> ResponseEntity<ApiResponse<T>> invalidAccessToken() {
        return fail(UNAUTHORIZED_CODE, INVALID_ACCESS_TOKEN);
    }

    public static <T> ResponseEntity<ApiResponse<T>> invalidRefreshToken() {
        return fail(UNAUTHORIZED_CODE, INVALID_REFRESH_TOKEN);
    }

    public static <T> ResponseEntity<ApiResponse<T>> notExpiredTokenYet() {
        return fail(BAD_REQUEST_CODE, NOT_EXPIRED_TOKEN_YET);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest() {
        return fail(BAD_REQUEST_CODE, BAD_REQUEST_MESSAGE);
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound() {
        return fail(NOT_FOUND_CODE, NOT_FOUND_MESSAGE);
    }

    public static <T> ResponseEntity<ApiResponse<T>> conflict() {
        return fail(CONFLICT_CODE, CONFLICT_MESSAGE);
    }

    public static <T> ResponseEntity<ApiResponse<T>> internalServerError() {
        return fail(INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_MESSAGE);
    }
}
