package com.tbp.honeyjar.login.service.admin;

import com.tbp.honeyjar.login.entity.admin.Admin;
import com.tbp.honeyjar.login.entity.user.User;
import com.tbp.honeyjar.login.mapper.admin.AdminMapper;
import com.tbp.honeyjar.login.oauth.info.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminService {

    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin getAdminByEmail(String email) {
        return adminMapper.findByEmail(email);
    }

    public void registerAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword())); // 비밀번호 암호화
        adminMapper.insertAdmin(admin);
    }

    private Admin updateAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        adminMapper.updateAdmin(admin);
        return admin;
    }
}
