package com.group11.service;

import com.group11.dto.CategoryDTO;
import com.group11.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.Optional;

public interface ICategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
    List<CategoryEntity> findAll();
    Optional<CategoryEntity> findById(Long id);
    CategoryEntity save(CategoryEntity category);
    void deleteById(Long id);
}
