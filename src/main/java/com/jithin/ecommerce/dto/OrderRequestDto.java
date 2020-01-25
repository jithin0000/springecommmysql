package com.jithin.ecommerce.dto;

import lombok.Data;


@Data
public class OrderRequestDto {
    private String orderNumber;
    private int orderTotal;
    private Long address_id;
}
