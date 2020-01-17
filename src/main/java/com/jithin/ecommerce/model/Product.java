package com.jithin.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Product extends BaseModel {

    @NotNull(message = "Name is an required field")
    private String name;
    @NotNull(message = "description is required field")
    private String description;
    @NotNull(message = "price is required field")
    private Long price;
    @NotNull(message = "quantity is required field")
    private int quantity;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private int rating = 0;

    private ESize size ;
    private String color;

    @ManyToMany
    @JoinTable(
            name = "product_property",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    private List<Property> properties = new ArrayList<>();

}
