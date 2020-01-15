package com.jithin.ecommerce.services;

import com.jithin.ecommerce.model.Category;
import com.jithin.ecommerce.model.Department;
import com.jithin.ecommerce.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DepartmentService extends BaseService<DepartmentRepository, Department> {
    protected DepartmentService(DepartmentRepository repository) {
        super(repository);
    }

    @Override
    public Department update(Long id, Department body) {
        return null;
    }

    public Page<Department> PaginateDepartment(int page, int size, String sort, String search){

        Page<Department> categories = null;
        if (!StringUtils.isEmpty(search))
        {
            categories = getRepository().findByNameContainingIgnoreCase(search, PageRequest.of(page, size, Sort.by(sort)));
        }else {

            categories = getRepository().findAll(PageRequest.of(page, size, Sort.by(sort)));
        }

        return categories;
    }

    public Iterable<Department> DepartmentsOfCategory(Long id) {
        return getRepository().findByCategoryId(id);
    }
}












