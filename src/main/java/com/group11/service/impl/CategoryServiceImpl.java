package com.group11.service.impl;

import com.group11.dto.CategoryDTO;
import com.group11.entity.CategoryEntity;
import com.group11.repository.CategoryRepository;
import com.group11.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, CategoryDTO.class))
                .collect(Collectors.toList());    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return modelMapper.map(entity, CategoryDTO.class);    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        CategoryEntity entity = modelMapper.map(categoryDTO, CategoryEntity.class);
        CategoryEntity savedEntity = categoryRepository.save(entity);
        return modelMapper.map(savedEntity, CategoryDTO.class);    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        CategoryEntity existingEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingEntity.setName(categoryDTO.getName());
        existingEntity.setDescription(categoryDTO.getDescription());
        CategoryEntity updatedEntity = categoryRepository.save(existingEntity);
        return modelMapper.map(updatedEntity, CategoryDTO.class);    }

    @Override
    public void deleteCategory(Long id) {
        CategoryEntity entity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(entity);
    }
}
