package com.jithin.ecommerce.services;

import com.jithin.ecommerce.exception.ProductNotFoundException;
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
        Product product = getRepository().findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(body.getName());
        product.setPrice(body.getPrice());
        product.setBrand(body.getBrand());
        product.setCart(body.getCart());
        product.setCategory(body.getCategory());
        product.setQuantity(body.getQuantity());
        product.setRating(body.getRating());
        product.setDescription(body.getDescription());
        product.setColors(body.getColors());
        product.setPhotos(body.getPhotos());
        product.setSize(body.getSize());
        return getRepository().save(product);
    }

    public Page<Product> getPaginatedResult(int page, int size, String sort, String search) {
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
                                             List<Long> brandIds,
                                             List<String> colorNameList,
                                             int min, int max) {

        Page<Product> products;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort));
        if (!StringUtils.isEmpty(search))
        {
            products = getRepository().findByNameContainingIgnoreCase(search, pageRequest);
        }else {

            products = getRepository().findAll(pageRequest);
        }

        if (!categoryIds.isEmpty()) {
            products = getRepository().findByCategoryIdIn(categoryIds, pageRequest);
        }

        if (!brandIds.isEmpty()) {
            products = getRepository().findByBrandIdIn(brandIds, pageRequest);
        }

        if (!colorNameList.isEmpty()) {
            products = getRepository().findByColors_NameIn(colorNameList, pageRequest);
        }

        if (min != 0 || max != 0)
        {
            products = getRepository().findByPriceBetween(min, max, pageRequest);
        }

        return products;
    }

}
