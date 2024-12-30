package com.paulotech.Desafio.backend.IFood.Api.domain.categories;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collation = "categories")
public class Category {
    @Id
    private String id;

    private String title;
    private String description;
    private String ownerId;
}
