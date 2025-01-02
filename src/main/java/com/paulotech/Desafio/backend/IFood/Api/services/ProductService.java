package com.paulotech.Desafio.backend.IFood.Api.services;

import com.paulotech.Desafio.backend.IFood.Api.domain.categories.Category;
import com.paulotech.Desafio.backend.IFood.Api.domain.categories.exceptions.CategoryNotFoundException;
import com.paulotech.Desafio.backend.IFood.Api.domain.products.ProductDTO;
import com.paulotech.Desafio.backend.IFood.Api.domain.products.Products;
import com.paulotech.Desafio.backend.IFood.Api.domain.products.exceptions.ProductsNotFoundExceptions;
import com.paulotech.Desafio.backend.IFood.Api.repositories.ProductRepository;
import com.paulotech.Desafio.backend.IFood.Api.services.aws.AwsSnsService;
import com.paulotech.Desafio.backend.IFood.Api.services.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final CategoryService categoryService;
    private final ProductRepository repository;
    private final AwsSnsService snsService;

    public ProductService(CategoryService categoryService, ProductRepository repository, AwsSnsService snsService){
        this.categoryService = categoryService;
        this.repository = repository;
        this.snsService = snsService;
    }

    public Products insert(ProductDTO productDTO){
        Category category =
                this.categoryService.getById(productDTO.categoryId()).orElseThrow(CategoryNotFoundException::new);
        Products newProduct = new Products(productDTO);
        newProduct.setCategory(category);
        this.repository.save(newProduct);
        this.snsService.publish(new MessageDTO(newProduct.getOwnerId()));
        return newProduct;
    }


    public Products update(String id, ProductDTO productData){
        Products products = this.repository.findById(id)
                .orElseThrow(ProductsNotFoundExceptions::new);

        if(productData.categoryId() != null) {
            this.categoryService.getById(productData.categoryId())
                    .ifPresent(products::setCategory);
        }

        if(!productData.title().isEmpty()) products.setTitle(productData.title());
        if(!productData.description().isEmpty()) products.setDescription(productData.description());
        if(!(productData.price() == null)) products.setPrice(productData.price());

        this.repository.save(products);
        this.snsService.publish(new MessageDTO(products.getOwnerId()));
        return products;
    }

    public void delete(String id){
        Products products = this.repository.findById(id)
                .orElseThrow(ProductsNotFoundExceptions::new);
        this.repository.delete(products);
    }

    public List<Products> getAll(){
        return this.repository.findAll();
    }
}

