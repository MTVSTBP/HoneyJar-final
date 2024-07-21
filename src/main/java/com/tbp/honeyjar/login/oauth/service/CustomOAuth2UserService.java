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

        User user = userMapper.findByKakaoId(userInfo.getId());

        if (user != null) {
            user = updateUser(user, userInfo);
        } else {
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
        user.setName(userInfo.getName());
        user.setUpdatedAt(LocalDateTime.now());

        userMapper.updateUser(user);
        return user;
    }
}
