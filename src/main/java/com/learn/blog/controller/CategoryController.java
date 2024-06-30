package com.learn.blog.controller;

import com.learn.blog.payload.CategoryDto;
import com.learn.blog.payload.CommonBody;
import com.learn.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategoryApi(@RequestBody CategoryDto form) {
        CategoryDto savedCategoryDto = categoryService.addCategory(form);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") Long categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAllCategories(CommonBody form
    ) {
        List<CategoryDto> categoryDtos = categoryService.getAllCategory(form);
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateById(@RequestBody CategoryDto form, @PathVariable(name = "id") Long categoryId) {
        CategoryDto savedCategoryDto = categoryService.updateById(categoryId, form);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") Long categoryId) {
        boolean deletedCategory = categoryService.deleteCategoryById(categoryId);

        if (deletedCategory) {
            return new ResponseEntity<>("Delete category successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Delete category failed", HttpStatus.BAD_REQUEST);
        }
    }
}
