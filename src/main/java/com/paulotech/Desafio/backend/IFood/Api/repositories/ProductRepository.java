package com.paulotech.Desafio.backend.IFood.Api.repositories;

import com.paulotech.Desafio.backend.IFood.Api.domain.products.Products;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Products, String> {
}
