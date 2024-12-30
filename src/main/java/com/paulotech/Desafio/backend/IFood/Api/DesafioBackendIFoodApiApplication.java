package com.paulotech.Desafio.backend.IFood.Api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DesafioBackendIFoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioBackendIFoodApiApplication.class, args);
	}

}
