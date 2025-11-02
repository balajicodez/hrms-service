package com.simplerp.hrms.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
