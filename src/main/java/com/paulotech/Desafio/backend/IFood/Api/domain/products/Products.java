package com.paulotech.Desafio.backend.IFood.Api.domain.products;

import com.paulotech.Desafio.backend.IFood.Api.domain.categories.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collation = "products")
public class Products {
    @Id
    private String id;

    private String title;
    private String description;
    private String ownerId;
    private Integer price;
    private Category category;
}
