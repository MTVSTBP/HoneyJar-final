package com.tbp.honeyjar.login.oauth.service;

import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import com.tbp.honeyjar.login.oauth.entity.ProviderType;
import com.tbp.honeyjar.login.oauth.entity.user.UserPrincipal;
import com.tbp.honeyjar.login.oauth.info.OAuth2UserInfo;
import com.tbp.honeyjar.login.oauth.info.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.debug("OAuth2User loaded: {}", oAuth2User);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error occurred while processing OAuth2 user", ex);
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(
            OAuth2UserRequest userRequest,
            OAuth2User oAuth2User
    ) {
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(ProviderType.KAKAO, oAuth2User.getAttributes());

        log.info("Processing OAuth2User: {}", userInfo.getId());
        log.info("OAuth2User attributes: {}", oAuth2User.getAttributes());

        User user = userMapper.findByKakaoId(userInfo.getId());

        if (user != null) {
            log.info("Existing user found: {}", user);
            if (!user.isUserStatus()) {
                // 탈퇴한 사용자가 다시 로그인한 경우
                log.info("Attempting to reactivate user account. User details: {}", user);
                user.setUserStatus(true);
                user.setFiredAt(null);
                userMapper.reactivateUser(user);
                log.info("User after reactivation: {}", user);
            }
            user = updateUser(user, userInfo);
        } else {
            log.info("Creating new user for KakaoId: {}", userInfo.getId());
            user = createUser(userInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo) {
        User user = User.builder()
                .kakaoId(userInfo.getId())
                .name(userInfo.getName())
                .pr("자기소개를 입력해 주세요.") // 기본 자기소개(pr) 필드 텍스트
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .userStatus(true)
                .build();

        userMapper.insertUser(user);
        return user;
    }

    private User updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getName().equals(userInfo.getName())) {
            user.setName(userInfo.getName());
        }
        user.setUpdatedAt(LocalDateTime.now());

        userMapper.updateUser(user);
        return user;
    }
}
