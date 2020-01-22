package com.jithin.ecommerce.repository;

import com.jithin.ecommerce.model.Category;
import com.jithin.ecommerce.model.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ColorRepository extends PagingAndSortingRepository<Color, Long> {
    Page<Color> findByNameContainingIgnoreCase(String search, PageRequest of);
}
