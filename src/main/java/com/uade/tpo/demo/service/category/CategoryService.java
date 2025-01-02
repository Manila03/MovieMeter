package com.uade.tpo.demo.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.exceptions.CategoryDuplicateException;

public interface CategoryService {
    Page<Category> getCategories(PageRequest pageRequest);
    
    Optional<Category> getCategoryById(Long categoryId);

    public Category createCategory(String description) throws CategoryDuplicateException;
}
