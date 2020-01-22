package com.jithin.ecommerce.services;

import com.jithin.ecommerce.model.Product;
import com.jithin.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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

    public Page<Product> getFilteredProducts(int page, int size, String sort, String search,
                                             List<Long> categoryIds,
                                             List<Long> brandIds
                                             ) {

        Page<Product> products;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort));
        if (!StringUtils.isEmpty(search))
        {
            products = getRepository().findByColors_NameIgnoreCase(search, pageRequest);
        }else {

            products = getRepository().findAll(pageRequest);
        }

        if (!categoryIds.isEmpty()) {
            products = getRepository().findByCategoryIdIn(categoryIds, pageRequest);
        }

        if (!brandIds.isEmpty()) {
            products = getRepository().findByBrandIdIn(brandIds, pageRequest);
        }

        return products;
    }

}
