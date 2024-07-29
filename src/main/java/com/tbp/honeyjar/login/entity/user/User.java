package com.tbp.honeyjar.login.entity.user;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long userId;
    private String kakaoId;
    private String pr;
    private String name; // 닉네임(?)
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate firedAt;
    private boolean userStatus;
}
