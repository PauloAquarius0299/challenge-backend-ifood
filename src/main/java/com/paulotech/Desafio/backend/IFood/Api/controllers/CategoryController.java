package com.paulotech.Desafio.backend.IFood.Api.controllers;

import com.paulotech.Desafio.backend.IFood.Api.domain.categories.Category;
import com.paulotech.Desafio.backend.IFood.Api.domain.categories.CategoryDTO;
import com.paulotech.Desafio.backend.IFood.Api.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryDTO categoryData){
       Category newCategory = this.service.insert(categoryData);
       return ResponseEntity.ok().body(newCategory);
    }
}
