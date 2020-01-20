package com.jithin.ecommerce.dto;

import com.jithin.ecommerce.model.Cart;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddUserToCart {

    @NotNull(message = "user id is required")
    private Long userId;
}
