package com.group11.restcontroller;

import com.group11.entity.CategoryEntity;
import com.group11.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    @Autowired
    CategoryRepository categoryRepository;
    @GetMapping
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

}
