package com.paulotech.Desafio.backend.IFood.Api.services;

import com.paulotech.Desafio.backend.IFood.Api.domain.categories.Category;
import com.paulotech.Desafio.backend.IFood.Api.domain.categories.CategoryDTO;
import com.paulotech.Desafio.backend.IFood.Api.domain.categories.exceptions.CategoryNotFoundException;
import com.paulotech.Desafio.backend.IFood.Api.repositories.CategoryRepository;
import com.paulotech.Desafio.backend.IFood.Api.services.aws.AwsSnsService;
import com.paulotech.Desafio.backend.IFood.Api.services.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    private final AwsSnsService snsService;

    public CategoryService(CategoryRepository repository, AwsSnsService snsService) {
        this.repository = repository;
        this.snsService = snsService;
    }

    public Category insert(CategoryDTO categoryData){
        Category newCategory = new Category(categoryData);
        this.repository.save(newCategory);
        this.snsService.publish(new MessageDTO(newCategory.toString()));
        return newCategory;
    }


    public Category update(String id, CategoryDTO categoryData){
        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
        if(!categoryData.title().isEmpty()) category.setTitle(categoryData.title());
        if(!categoryData.description().isEmpty()) category.setDescription(categoryData.description());

        this.repository.save(category);
        this.snsService.publish(new MessageDTO(category.toString()));
        return category;
    }

    public void delete(String id){
        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
        this.repository.delete(category);

        String deleteMessage = String.format("Category with ID %s and Title '%s' has been deleted.",
                category.getId(), category.getTitle());
        this.snsService.publish(new MessageDTO(deleteMessage));
    }

    public List<Category> getAll(){
        return this.repository.findAll();
    }

    public Optional<Category> getById(String id){
        return this.repository.findById(id);
    }

}
