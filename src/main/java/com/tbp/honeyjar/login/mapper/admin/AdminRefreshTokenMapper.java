package com.tbp.honeyjar.login.mapper.admin;

import com.tbp.honeyjar.login.entity.admin.AdminRefreshToken;
import com.tbp.honeyjar.login.entity.user.UserRefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminRefreshTokenMapper {
    AdminRefreshToken findByAdminId(Long adminId);

    void insertAdminRefreshToken(AdminRefreshToken adminRefreshToken);
    void updateAdminRefreshToken(AdminRefreshToken adminRefreshToken);
    void deleteAdminRefreshToken(Long adminId);
}
