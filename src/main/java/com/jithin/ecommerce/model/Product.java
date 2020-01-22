package com.jithin.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int price;
    @NotNull(message = "quantity is required field")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    private int rating = 0;

    private ESize size;

    @ManyToMany
    @JoinTable(
            name = "product_property",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    private List<Property> properties = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Photo> photos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "product_color",
            joinColumns = {
                    @JoinColumn(name = "product_id"),
            },
            inverseJoinColumns = {@JoinColumn(name = "color_id")}

    )
    private List<Color> colors = new ArrayList<>();

}
