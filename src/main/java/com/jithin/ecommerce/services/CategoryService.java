package com.jithin.ecommerce.services;

import com.jithin.ecommerce.exception.CategoryNotFoundException;
import com.jithin.ecommerce.model.Category;
import com.jithin.ecommerce.repository.CategoryRepository;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public Page<Category> PaginatedCategoryList(int page, int size, String sort, String search){

        Page<Category> categories = null;
        if (!StringUtils.isEmpty(search))
        {
            categories = getRepository().findByNameContainingIgnoreCase(search, PageRequest.of(page, size, Sort.by(sort)));
        }else {

         categories = getRepository().findAll(PageRequest.of(page, size, Sort.by(sort)));
        }

        return categories;

    }
}
