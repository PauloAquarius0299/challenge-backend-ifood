package com.paulotech.Desafio.backend.IFood.Api.services;

import com.paulotech.Desafio.backend.IFood.Api.domain.categories.Category;
import com.paulotech.Desafio.backend.IFood.Api.domain.categories.exceptions.CategoryNotFoundException;
import com.paulotech.Desafio.backend.IFood.Api.domain.products.ProductDTO;
import com.paulotech.Desafio.backend.IFood.Api.domain.products.Products;
import com.paulotech.Desafio.backend.IFood.Api.domain.products.exceptions.ProductsNotFoundExceptions;
import com.paulotech.Desafio.backend.IFood.Api.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private CategoryService categoryService;
    private ProductRepository repository;

    public ProductService(CategoryService categoryService, ProductRepository productRepository){
        this.categoryService = categoryService;
        this.repository = repository;
    }

    public Products insert(ProductDTO productDTO){
        Category category =
                this.categoryService.getById(productDTO.categoryId()).orElseThrow(CategoryNotFoundException::new);
        Products newProduct = new Products(productDTO);
        newProduct.setCategory(category);
        this.repository.save(newProduct);
        return newProduct;
    }

    public List<Products> getAll(){
        return this.repository.findAll();
    }

    public Products update(String id, ProductDTO productData){
        Products products = this.repository.findById(id)
                .orElseThrow(ProductsNotFoundExceptions::new);

        this.categoryService.getById(productData.categoryId()).ifPresent(products::setCategory);

        if(!productData.title().isEmpty()) products.setTitle(productData.title());
        if(!productData.description().isEmpty()) products.setDescription(productData.description());
        if(!(productData.price() == null)) products.setPrice(productData.price());

        this.repository.save(products);
        return products;
    }

    public void delete(String id){
        Products products = this.repository.findById(id)
                .orElseThrow(ProductsNotFoundExceptions::new);
        this.repository.delete(products);
    }
}
