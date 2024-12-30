package com.paulotech.Desafio.backend.IFood.Api.repositories;

import com.paulotech.Desafio.backend.IFood.Api.domain.categories.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
