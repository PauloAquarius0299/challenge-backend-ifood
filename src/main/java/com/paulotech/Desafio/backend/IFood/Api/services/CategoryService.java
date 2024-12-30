package com.paulotech.Desafio.backend.IFood.Api.services;

import com.paulotech.Desafio.backend.IFood.Api.domain.categories.Category;
import com.paulotech.Desafio.backend.IFood.Api.domain.categories.CategoryDTO;
import com.paulotech.Desafio.backend.IFood.Api.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository repository;

    public CategoryService(CategoryRepository repository){
        this.repository = repository;
    }

    public Category insert(CategoryDTO categoryData){
        Category newCategory = new Category(categoryData);
        this.repository.save(newCategory);
        return newCategory;
    }
}
