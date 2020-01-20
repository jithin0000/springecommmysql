package com.jithin.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Cart extends BaseModel {

    @OneToMany(mappedBy = "cart")
    private List<Product> products = new ArrayList<>();

    private int total = 0;


    public void addToCart(Product product){
        this.products.add(product);
        product.setCart(this);
    }

    public void removeFromCart(Product product) {
        this.products.remove(product);
        product.setCart(null);
    }

}
