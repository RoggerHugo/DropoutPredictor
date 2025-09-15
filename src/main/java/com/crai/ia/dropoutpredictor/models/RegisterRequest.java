package com.crai.ia.dropoutpredictor.models;

import com.crai.ia.dropoutpredictor.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private Role role = Role.USER;
}