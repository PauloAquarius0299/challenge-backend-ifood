package com.paulotech.Desafio.backend.IFood.Api.controllers;

import com.paulotech.Desafio.backend.IFood.Api.domain.products.ProductDTO;
import com.paulotech.Desafio.backend.IFood.Api.domain.products.Products;
import com.paulotech.Desafio.backend.IFood.Api.services.ProductService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductsController {
    private ProductService service;

    public ProductsController(ProductService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Products> insert(@RequestBody ProductDTO productData){
        Products newProducts = this.service.insert(productData);
        return ResponseEntity.ok().body(newProducts);
    }

    @GetMapping
    public ResponseEntity<List<Products>> getAll(){
        List<Products> products = this.service.getAll();
        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> update(@PathVariable("id") String id, @RequestBody ProductDTO productData){
        Products updatedProducts = this.service.update(id, productData);
        return ResponseEntity.ok().body(updatedProducts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Products> delete(@PathVariable("id") String id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
