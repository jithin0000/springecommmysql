package com.jithin.ecommerce.bootstrap;

import com.github.javafaker.Faker;
import com.jithin.ecommerce.model.Category;
import com.jithin.ecommerce.model.Department;
import com.jithin.ecommerce.model.Photo;
import com.jithin.ecommerce.model.Product;
import com.jithin.ecommerce.services.CategoryService;
import com.jithin.ecommerce.services.DepartmentService;
import com.jithin.ecommerce.services.PhotoService;
import com.jithin.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    ProductService productService;

    @Autowired
    PhotoService photoService;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

        generateCategories(faker);

    }


    private void generateDepartment(Faker faker, Long categoryid) {
        for (int i = 1; i < 2; i++) {
            Department department = new Department();
            department.setName(faker.company().name());
            department.setCategory(categoryService.get(categoryid).get());
            departmentService.create(department);
        }
    }

    private void generateCategories(Faker faker) {

        for (int i = 0; i < 15; i++) {
            Category category = new Category();
            category.setName(faker.commerce().department());

            Category category1 = categoryService.create(category);

            generateDepartment(faker, category1.getId());
        }

        generateProduct(faker);
    }

    private void generateProduct(Faker faker) {
        for (int i = 0; i < 25; i++) {
            Product product = new Product();
            product.setName(faker.artist().name());
            product.setDescription(faker.lorem().sentence(20));
            product.setQuantity(faker.number().randomDigitNotZero());
            product.setPrice(Long.valueOf(faker.number().numberBetween(100, 900)));
            product.setColor(faker.color().name());
            product.setCategory(categoryService.get((long) faker.number().numberBetween(1, 14)).get());

            generatePhoto(faker, productService.create(product));
        }
    }

    private void generatePhoto(Faker faker, Product product) {
        for (int i = 0; i < 6; i++) {

            Photo photo = new Photo();
            photo.setImageUrl(faker.internet().image());
            photo.setProduct(product);
            photoService.create(photo);
        }
    }


}
