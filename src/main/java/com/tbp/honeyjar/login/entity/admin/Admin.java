package com.tbp.honeyjar.login.entity.admin;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private Long adminId;
    private String email;
    private String password;
}