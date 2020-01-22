package com.jithin.ecommerce.bootstrap;

import com.github.javafaker.Faker;
import com.jithin.ecommerce.exception.BrandNotFoundException;
import com.jithin.ecommerce.exception.ColorNotFoundException;
import com.jithin.ecommerce.model.*;
import com.jithin.ecommerce.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

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

    @Autowired
    private ColorService colorService;
    @Autowired
    private BrandService brandService;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

//        generateColors(faker);
//
//        generateBrand(faker);
//
//        generateCategories(faker);


    }

    private void generateBrand(Faker faker) {

        for (int i = 0; i < 8; i++) {
            Brand brand = new Brand();
            brand.setName(faker.company().name());
            brandService.create(brand);
        }
    }

    private void generateColors(Faker faker) {

        for (int i = 0; i < 8; i++) {
            Color color = new Color();
            color.setName(faker.color().name());
            colorService.create(color);
        }

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

        String[] categories = new String[]{"MEN", "WOMEN", "KIDS","OTHER"};

        for (int i = 0; i < 4; i++) {
            Category category = new Category();
            category.setName(categories[i]);

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
            product.setPrice(faker.number().numberBetween(100, 900));
            product.setCategory(categoryService.get((long) faker.number().numberBetween(1, 4)).get());

            product.getColors().add(colorService.get((long) faker.number().numberBetween(1, 3)).orElseThrow(() -> new ColorNotFoundException()));
            product.getColors().add(colorService.get((long) faker.number().numberBetween(4,8)).orElseThrow(() -> new ColorNotFoundException()));

            product.setBrand(brandService.get((long) faker.number().numberBetween(1, 8))
                    .orElseThrow(() -> new BrandNotFoundException((long) 1)));
            generatePhoto(faker, productService.create(product));



        }
    }

    private void generatePhoto(Faker faker, Product product) {
        for (int i = 0; i < 6; i++) {

            Photo photo = new Photo();
            photo.setImageUrl(faker.avatar().image());
            photo.setProduct(product);
            photoService.create(photo);
        }
    }


}
