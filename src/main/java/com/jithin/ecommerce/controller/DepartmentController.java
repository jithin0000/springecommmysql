package com.jithin.ecommerce.controller;

import com.jithin.ecommerce.dto.DeleteResponseDto;
import com.jithin.ecommerce.exception.DepartmentNotFoundException;
import com.jithin.ecommerce.model.Department;
import com.jithin.ecommerce.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.jithin.ecommerce.utils.constants.API_BASE;

@RestController
@RequestMapping(API_BASE + "/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    public ResponseEntity<Iterable<Department>> getAll(
            @RequestParam(name = "page", defaultValue = "0") String page,
            @RequestParam(name = "size", defaultValue = "10") String size,
            @RequestParam(name = "sort", defaultValue = "createAt") String sort,
            @RequestParam(name = "search", required = false) String search
    ) {
        int page_num = Integer.parseInt(page);
        int item_size = Integer.parseInt(size);

        return new ResponseEntity<>(departmentService.PaginateDepartment(page_num, item_size, sort, search), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getDepartmentsOfCategory(@PathVariable Long id) {
        Iterable<Department> departments = departmentService.DepartmentsOfCategory(id);
        return ResponseEntity.ok(departments);
    }


    @PostMapping("/new")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department job) {
        return new ResponseEntity<>(departmentService.create(job), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        Department job = departmentService.get(id).orElseThrow(() -> new DepartmentNotFoundException(id));
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        DeleteResponseDto message = departmentService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @Valid @RequestBody Department body) {

        return new ResponseEntity<>(departmentService.update(id, body), HttpStatus.OK);
    }








}
