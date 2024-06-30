package com.learn.blog.service;

import com.learn.blog.entity.Category;
import com.learn.blog.exception.ResourceNotFoundException;
import com.learn.blog.payload.CategoryDto;
import com.learn.blog.payload.CommonBody;
import com.learn.blog.repository.CategoryRepository;
import com.learn.blog.utils.CommonFn;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category savedCategory = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(savedCategory);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId)
        );
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory(CommonBody form) {
        Sort sort = CommonFn.commonSort(form.getSortBy(), form.getSortDir());
        Pageable pageable = PageRequest.of(form.getPageNumber(), form.getPageSize(), sort);
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<CategoryDto> res = categories.getContent().stream().map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        return res;
    }

    @Override
    public boolean deleteCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId)
        );
        categoryRepository.delete(category);
        return true;
    }

    @Override
    public CategoryDto updateById(Long categoryId, CategoryDto categoryDto) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId)
        );

        if(categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }

        if(categoryDto.getDescription() != null) {
            category.setDescription(categoryDto.getDescription());
        }

        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }
}
