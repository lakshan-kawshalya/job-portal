package com.lakshankawshalya.job_portal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String role;  // ROLE_USER, ROLE_EMPLOYER, ROLE_ADMIN
}