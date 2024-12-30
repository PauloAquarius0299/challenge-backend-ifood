package com.paulotech.Desafio.backend.IFood.Api.repositories;

import com.paulotech.Desafio.backend.IFood.Api.domain.products.Products;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Products, String> {
}
