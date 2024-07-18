package com.tbp.honeyjar.login.entity.admin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRefreshToken {
    private Long adminId;
    private String refreshToken;
}
