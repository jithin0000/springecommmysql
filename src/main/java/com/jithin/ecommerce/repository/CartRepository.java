package com.jithin.ecommerce.repository;

import com.jithin.ecommerce.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
