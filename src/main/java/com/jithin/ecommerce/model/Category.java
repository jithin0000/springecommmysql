package com.jithin.ecommerce.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Category extends BaseModel {

    @NotNull(message = "this field is required")
    private String name;

}
