package com.tbp.honeyjar.login.oauth.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

public class OAuthProviderMissMatchException extends OAuth2AuthenticationException {

    public OAuthProviderMissMatchException(String message) {
        super(new OAuth2Error("oauth_provider_mismatch", message, null)); // OAuth2Error 사용
    }
}
