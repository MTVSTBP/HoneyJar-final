package com.tbp.honeyjar.login.oauth.service;

import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.user.UserMapper;
import com.tbp.honeyjar.login.oauth.entity.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userMapper.findByUserName(name);
        if (user == null) {
            throw new UsernameNotFoundException("Can not find name.");
        }

        return UserPrincipal.create(user);
    }
}
