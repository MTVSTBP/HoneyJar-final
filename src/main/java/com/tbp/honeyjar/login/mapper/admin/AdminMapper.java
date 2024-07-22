package com.tbp.honeyjar.login.mapper.admin;

import com.tbp.honeyjar.login.entity.admin.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    Admin findByAdminId(Long adminId); // 이메일로 관리자 조회
    Admin findByEmail(String email); // 이메일로 관리자 조회
    void insertAdmin(Admin admin);
    void updateAdmin(Admin admin);
}
