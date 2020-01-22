package com.jithin.ecommerce.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Brand extends BaseModel {
    @NotNull(message = "Name field is required")
    private String name;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "brand")
//    private List<Product> products = new ArrayList<>();

}
