package com.jithin.ecommerce.dto;

import com.jithin.ecommerce.model.BaseModel;
import lombok.Data;

@Data
public class CartResponse {

    private boolean added = false;
    private int total;
    private int productCount;

}
