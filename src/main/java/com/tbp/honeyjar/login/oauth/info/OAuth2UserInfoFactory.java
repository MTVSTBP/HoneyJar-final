package com.tbp.honeyjar.login.oauth.info;

import com.tbp.honeyjar.login.oauth.entity.ProviderType;
import com.tbp.honeyjar.login.oauth.info.impl.KakaoOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(
            ProviderType providerType,
            Map<String, Object> attributes
    ) {
        return switch (providerType) {
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);
            default -> throw new IllegalArgumentException("Invalid Provider Type.");
        };
    }
}
