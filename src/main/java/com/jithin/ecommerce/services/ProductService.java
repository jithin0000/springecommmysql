package com.jithin.ecommerce.services;

import com.jithin.ecommerce.model.Product;
import com.jithin.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductService extends BaseService<ProductRepository, Product> {
    protected ProductService(ProductRepository repository) {
        super(repository);
    }

    @Override
    public Product update(Long id, Product body) {
        return null;
    }

    public Page<Product> PaginatedProductList(int page, int size, String sort, String search) {
        Page<Product> products;
        if (!StringUtils.isEmpty(search))
        {
            products = getRepository().findByNameContainingIgnoreCase(search, PageRequest.of(page, size, Sort.by(sort)));
        }else {

            products = getRepository().findAll(PageRequest.of(page, size, Sort.by(sort)));
        }

        return products;

    }
}
