package com.jithin.ecommerce.services;

import com.jithin.ecommerce.exception.CategoryNotFoundException;
import com.jithin.ecommerce.model.Category;
import com.jithin.ecommerce.repository.CategoryRepository;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService<CategoryRepository, Category> {

    protected CategoryService(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public Category update(Long id, Category body) {

        Category category = getRepository().findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        category.setName(body.getName());

        Category newCategory = getRepository().save(category);

        return newCategory;
    }
}
