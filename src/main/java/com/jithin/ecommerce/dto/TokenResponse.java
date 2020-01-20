package com.jithin.ecommerce.dto;

import lombok.Data;

@Data
public class TokenResponse {

    private boolean success;
    private String token;

    public TokenResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }
}
