package com.jithin.ecommerce.services;

import com.jithin.ecommerce.model.ProductOrder;
import com.jithin.ecommerce.repository.ProductOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderService extends BaseService<ProductOrderRepository, ProductOrder> {
    protected ProductOrderService(ProductOrderRepository repository) {
        super(repository);
    }

    @Override
    public ProductOrder update(Long id, ProductOrder body) {
        return null;
    }
}
