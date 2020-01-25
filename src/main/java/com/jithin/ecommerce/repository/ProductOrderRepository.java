package com.jithin.ecommerce.repository;

import com.jithin.ecommerce.model.ProductOrder;
import org.springframework.data.repository.CrudRepository;

public interface ProductOrderRepository extends CrudRepository<ProductOrder, Long> {
}
