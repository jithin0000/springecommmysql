package com.jithin.ecommerce.controller;

import com.jithin.ecommerce.dto.DeleteResponseDto;
import com.jithin.ecommerce.exception.BrandNotFoundException;
import com.jithin.ecommerce.exception.InvalidLoginResponse;
import com.jithin.ecommerce.exception.ProductNotFoundException;
import com.jithin.ecommerce.model.Brand;
import com.jithin.ecommerce.model.Product;
import com.jithin.ecommerce.model.User;
import com.jithin.ecommerce.services.BrandService;
import com.jithin.ecommerce.services.DepartmentService;
import com.jithin.ecommerce.services.ProductService;
import com.jithin.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.jithin.ecommerce.utils.constants.API_BASE;

@RestController
@RequestMapping(API_BASE + "/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(brandService.getAll());
    }


    @PostMapping("/new")
    public ResponseEntity<Brand> createBrand(@Valid @RequestBody Brand brand) {
        return new ResponseEntity<>(brandService.create(brand), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrand(@PathVariable Long id) {
        Brand brand = brandService.get(id).orElseThrow(() -> new BrandNotFoundException(id));
        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        DeleteResponseDto message = brandService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @Valid @RequestBody Brand body) {
        return new ResponseEntity<>(brandService.update(id, body), HttpStatus.OK);
    }

}
