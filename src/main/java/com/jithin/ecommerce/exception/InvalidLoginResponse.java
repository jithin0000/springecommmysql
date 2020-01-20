package com.jithin.ecommerce.exception;

import lombok.Data;

@Data
public class InvalidLoginResponse {

    private String username;
    private String password;
    public InvalidLoginResponse() {

        this.username = "invalid username";
        this.password = "invalid password";
    }
}
