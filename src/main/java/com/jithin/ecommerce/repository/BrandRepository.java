package com.jithin.ecommerce.repository;

import com.jithin.ecommerce.model.Brand;
import com.jithin.ecommerce.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository extends CrudRepository<Brand, Long> {
}
