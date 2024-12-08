package com.group11.service;

import com.group11.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
	List<CategoryEntity> findAll();
	Optional<CategoryEntity> findById(Long id);
	CategoryEntity save(CategoryEntity category);
	void deleteById(Long id);
	
}
