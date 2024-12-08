package com.group11.service;

import com.group11.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}
