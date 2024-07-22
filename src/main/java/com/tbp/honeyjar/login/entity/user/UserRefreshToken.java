package com.tbp.honeyjar.login.entity.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRefreshToken {
    private Long userId;
    private String refreshToken;
}
