package com.tbp.honeyjar.login.oauth.info.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbp.honeyjar.login.oauth.info.OAuth2UserInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> kakaoAccount = objectMapper.convertValue(attributes.get("kakao_account"), Map.class);
        if (kakaoAccount != null) {
            Map<String, Object> profile = objectMapper.convertValue(kakaoAccount.get("profile"), Map.class);
            if (profile != null) {
                return (String) profile.get("nickname");
            }
        }
        return null;
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = objectMapper.convertValue(attributes.get("kakao_account"), Map.class);
        if (kakaoAccount != null) {
            Boolean emailNeedsAgreement = objectMapper.convertValue(kakaoAccount.get("email_needs_agreement"), Boolean.class);
            // email_needs_agreement 확인
            if (emailNeedsAgreement == null || !emailNeedsAgreement) {
                return (String) kakaoAccount.get("email");
            }
        }

        // 이메일이 없는 경우 닉네임을 사용하여 가상의 이메일 생성(nickname@kakao.com)
        String nickname = getName();
        return nickname != null ? nickname + "@kakao.com" : null;
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> kakaoAccount = objectMapper.convertValue(attributes.get("kakao_account"), Map.class);
        if (kakaoAccount != null) {
            Map<String, Object> profile = objectMapper.convertValue(kakaoAccount.get("profile"), Map.class);
            // profile_image_needs_agreement 확인
            if (profile != null) {
                Boolean profileImageNeedsAgreement = objectMapper.convertValue(kakaoAccount.get("profile_image_needs_agreement"), Boolean.class);
                if (profileImageNeedsAgreement == null || !profileImageNeedsAgreement) {
                    return (String) profile.get("profile_image_url");
                }
            }
        }

        return null;
    }
}
