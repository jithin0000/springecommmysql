package com.jithin.ecommerce.repository;

import com.jithin.ecommerce.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
