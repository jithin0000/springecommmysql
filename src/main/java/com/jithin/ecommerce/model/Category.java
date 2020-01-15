package com.jithin.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Category extends BaseModel {

    @NotNull(message = "this field is required")
    private String name;

}
