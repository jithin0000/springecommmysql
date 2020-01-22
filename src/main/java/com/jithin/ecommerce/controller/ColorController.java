package com.jithin.ecommerce.controller;

import com.jithin.ecommerce.dto.DeleteResponseDto;
import com.jithin.ecommerce.exception.ColorNotFoundException;
import com.jithin.ecommerce.exception.DepartmentNotFoundException;
import com.jithin.ecommerce.model.Color;
import com.jithin.ecommerce.model.Department;
import com.jithin.ecommerce.services.ColorService;
import com.jithin.ecommerce.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.jithin.ecommerce.utils.constants.API_BASE;

@RestController
@RequestMapping(API_BASE + "/color")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping("")
    public ResponseEntity<Iterable<Color>> getAll(
            @RequestParam(name = "page", defaultValue = "0") String page,
            @RequestParam(name = "size", defaultValue = "10") String size,
            @RequestParam(name = "sort", defaultValue = "createAt") String sort,
            @RequestParam(name = "search", required = false) String search
    ) {
        int page_num = Integer.parseInt(page);
        int item_size = Integer.parseInt(size);

        return new ResponseEntity(colorService.paginatedColorList(page_num, item_size, sort, search), HttpStatus.OK);
    }


    @PostMapping("/new")
    public ResponseEntity<Color> createColor(@Valid @RequestBody Color color) {

        return new ResponseEntity<>(colorService.create(color), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> getColor(@PathVariable Long id) {
        Color job = colorService.get(id).orElseThrow(() -> new ColorNotFoundException());
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteColor(@PathVariable Long id) {
        DeleteResponseDto message = colorService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Color> updateColor(@PathVariable Long id, @Valid @RequestBody Color body) {

        return new ResponseEntity<>(colorService.update(id, body), HttpStatus.OK);
    }


}
