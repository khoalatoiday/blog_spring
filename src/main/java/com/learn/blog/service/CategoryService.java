package com.learn.blog.service;

import com.learn.blog.payload.CategoryDto;
import com.learn.blog.payload.CommonBody;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(Long categoryId);

    List<CategoryDto> getAllCategory(CommonBody commonBody);

    boolean deleteCategoryById(Long categoryId);

    CategoryDto updateById(Long categoryId,CategoryDto categoryDto);
}
