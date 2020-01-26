package com.jithin.ecommerce.controller;

import com.jithin.ecommerce.dto.DeleteResponseDto;
import com.jithin.ecommerce.dto.ProductRequestDto;
import com.jithin.ecommerce.dto.converter.ProductFilterRequestDto;
import com.jithin.ecommerce.dto.converter.ProductRequestDtoToProduct;
import com.jithin.ecommerce.exception.CategoryNotFoundException;
import com.jithin.ecommerce.exception.PhotoNotFoundException;
import com.jithin.ecommerce.exception.ProductNotFoundException;
import com.jithin.ecommerce.model.Category;
import com.jithin.ecommerce.model.Photo;
import com.jithin.ecommerce.model.Product;
import com.jithin.ecommerce.services.CategoryService;
import com.jithin.ecommerce.services.PhotoService;
import com.jithin.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.jithin.ecommerce.utils.constants.API_BASE;

@RestController
@RequestMapping(API_BASE + "/product")
public class  ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductRequestDtoToProduct productRequestDtoToProductConverter;

    @Autowired
    private PhotoService photoService;

    @GetMapping("")
    public ResponseEntity<Iterable<Product>> getAll(
            @RequestParam(name = "page", defaultValue = "0") String page,
            @RequestParam(name = "size", defaultValue = "10") String size,
            @RequestParam(name = "sort", defaultValue = "createAt") String sort,
            @RequestParam(name = "search", required = false) String search
    ) {
        int page_num = Integer.parseInt(page);
        int item_size = Integer.parseInt(size);

        return new ResponseEntity<>(productService.getPaginatedResult(page_num, item_size, sort, search), HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> getProductsByFiltering(@Valid @RequestBody ProductFilterRequestDto filterRequestDto) {

        return ResponseEntity.ok(productService.getFilteredProducts(
                filterRequestDto.getPage(), filterRequestDto.getPageSize(),filterRequestDto.getSort(),
                filterRequestDto.getSearch(),filterRequestDto.getCategories(),
                filterRequestDto.getBrands(), filterRequestDto.getColors(),
                filterRequestDto.getMin(),
                filterRequestDto.getMax()
        ));
    }




    @PostMapping("/new")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {

       Product product =  productRequestDtoToProductConverter.convert(productRequestDto);
        Category category = categoryService.get(productRequestDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productRequestDto.getCategory()));

        for (Long photoId : productRequestDto.getPhotos()) {

            Photo photo = photoService.get(photoId).orElseThrow(() -> new PhotoNotFoundException(photoId));

            product.getPhotos().add(photo);
            photo.setProduct(product);
        }

        product.setCategory(category);

        return new ResponseEntity<>(productService.create(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product job = productService.get(id).orElseThrow(() -> new ProductNotFoundException(id));
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        DeleteResponseDto message = productService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product body) {

        return new ResponseEntity<>(productService.update(id, body), HttpStatus.OK);
    }


}
