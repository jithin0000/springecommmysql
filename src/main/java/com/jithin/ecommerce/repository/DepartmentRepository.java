package com.jithin.ecommerce.repository;

import com.jithin.ecommerce.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {
    Page<Department> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Iterable<Department> findByCategoryId(Long categoryId);
}
