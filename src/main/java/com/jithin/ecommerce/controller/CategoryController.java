package com.jithin.ecommerce.controller;

import com.jithin.ecommerce.exception.CategoryNotFoundException;
import com.jithin.ecommerce.model.Category;
import com.jithin.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.jithin.ecommerce.utils.constants.API_BASE;

@RestController
@RequestMapping(API_BASE+"/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<Iterable<Category>> getAll(
            @RequestParam(name = "page", defaultValue = "0") String page,
            @RequestParam(name = "size", defaultValue = "10") String size,
            @RequestParam(name = "sort", defaultValue = "createAt") String sort,
            @RequestParam(name = "search", required = false) String search
    ) {
        int page_num = Integer.parseInt(page);
        int item_size = Integer.parseInt(size);

        return new ResponseEntity<>(categoryService.PaginatedCategoryList(page_num, item_size, sort, search), HttpStatus.OK);
    }


    @PostMapping("/new")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category job) {
        return new ResponseEntity<>(categoryService.create(job), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Category job = categoryService.get(id).orElseThrow(() -> new CategoryNotFoundException(id));
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        String message = categoryService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category body) {

        return new ResponseEntity<>(categoryService.update(id, body), HttpStatus.OK);
    }

}
