package com.jithin.ecommerce.repository;

import com.jithin.ecommerce.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
