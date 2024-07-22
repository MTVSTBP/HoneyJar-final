package com.tbp.honeyjar;

import com.tbp.honeyjar.login.config.properties.AppProperties;
import com.tbp.honeyjar.login.config.properties.CorsProperties;
import com.tbp.honeyjar.login.entity.admin.Admin;
import com.tbp.honeyjar.login.service.admin.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({
        CorsProperties.class,
        AppProperties.class
})
@MapperScan(basePackages = "com.tbp.honeyjar")
public class HoneyJarApplication {
    public static void main(String[] args) {
        SpringApplication.run(HoneyJarApplication.class, args);
    }

    @Bean

    public CommandLineRunner commandLineRunner(AdminService adminService) {

        return args -> {
            String adminEmail = "admin@admin.com";
            String adminPassword = "!N%yLC@#E9xM";

            // 기존 관리자 계정 있는지 확인
            if (adminService.getAdminByEmail(adminEmail) == null) {
                Admin admin = Admin.builder()
                        .email(adminEmail)
                        .password(adminPassword) // 비밀번호 암호화
                        .build();
                adminService.registerAdmin(admin);
                log.info("Admin 계정이 생성되었습니다.");
            } else {
                log.warn("Admin 계정이 이미 존재합니다.");
            }
        };
    }
}
