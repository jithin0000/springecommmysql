package com.jithin.ecommerce.dto;

import lombok.Data;

@Data
public class UserNameAlreadyExistResponse {
    private String username;

    public UserNameAlreadyExistResponse(String username) {
        this.username = username;
    }
}
