package com.jithin.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Color extends BaseModel {

    @NotNull(message = "name field is required")
    private String name;

    @ManyToMany(mappedBy = "colors")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();
}
