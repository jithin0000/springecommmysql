package com.jithin.ecommerce.bootstrap;

import com.github.javafaker.Faker;
import com.jithin.ecommerce.model.Category;
import com.jithin.ecommerce.model.Department;
import com.jithin.ecommerce.services.CategoryService;
import com.jithin.ecommerce.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DepartmentService departmentService;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

        generateCategories(faker);
        generateDepartment(faker);

    }

    private void generateDepartment(Faker faker) {
        for (int i = 1; i < 15; i++) {
            Department department = new Department();
            department.setName(faker.company().name());
            department.setCategory(categoryService.get((long) i).get());
            departmentService.create(department);
        }
    }

    private void generateCategories(Faker faker) {

        for (int i = 0; i < 15; i++) {
            Category category = new Category();
            category.setName(faker.commerce().department());
            categoryService.create(category);
        }

    }
}
