package com.tbp.honeyjar.login.oauth.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    USER("USER"),
    ADMIN("ADMIN");

    private final String code;
}
