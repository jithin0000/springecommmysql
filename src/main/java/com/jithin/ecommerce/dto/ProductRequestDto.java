package com.jithin.ecommerce.dto;

import com.jithin.ecommerce.model.Category;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
public class ProductRequestDto {

    @NotNull(message = "Name is an required field")
    private String name;
    @NotNull(message = "description is required field")
    private String description;
    @NotNull(message = "price is required field")
    private Long price;
    @NotNull(message = "quantity is required field")
    private int quantity;

    @NotNull(message = "category is required fiele")
    private long category_id;

    private int rating = 0;
}
