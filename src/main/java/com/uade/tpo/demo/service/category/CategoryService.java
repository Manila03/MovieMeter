package com.uade.tpo.demo.service.category;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.exceptions.CategoryDuplicateException;

public interface CategoryService {
    List<Category> getCategories();
    Optional<Category> getCategoryById(Long categoryId);

    public Category createCategory(String description) throws CategoryDuplicateException;
}
