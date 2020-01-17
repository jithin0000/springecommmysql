package com.jithin.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Property extends BaseModel {

    @NotNull(message = "name is required field")
    private String name;

    @ManyToMany(mappedBy = "properties")
    List<Product> products;
}
