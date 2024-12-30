package com.paulotech.Desafio.backend.IFood.Api.repositories;

import com.paulotech.Desafio.backend.IFood.Api.domain.categories.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
}
