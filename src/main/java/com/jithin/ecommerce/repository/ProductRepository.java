package com.jithin.ecommerce.repository;

import com.jithin.ecommerce.model.Category;
import com.jithin.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByCategoryIdIn(List<Long> ids, Pageable pageable);
    Page<Product> findByBrandIdIn(List<Long> ids, Pageable pageable);

    Page<Product> findByColors_NameIgnoreCase(String name, Pageable pageable);

}
