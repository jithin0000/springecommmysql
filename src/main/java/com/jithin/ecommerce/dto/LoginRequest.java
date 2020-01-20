package com.jithin.ecommerce.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotNull(message = "username is required field")
    private String username;
    @NotNull(message = "password is required field")
    private String password;
}
