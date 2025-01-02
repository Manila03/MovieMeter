package com.uade.tpo.demo.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.exceptions.CategoryDuplicateException;
import com.uade.tpo.demo.repository.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // public CategoryServiceImpl() {
    //     categoryRepository = new CategoryRepository();
    // }

    // No necesite crear un constructor porque Spring lo hace por nosotros al usar el @autowired 
    // y habiendo declarado @Repository en CategoryRepository

    @Override
    public Page<Category> getCategories(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest);
    }
    
    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Category createCategory(String description) throws CategoryDuplicateException {
        List<Category> match = categoryRepository.findByDescription(description);
        if(match.size() > 0) {
            throw new CategoryDuplicateException();
        } else {
            return categoryRepository.save(new Category(description));
        }
    }
}
