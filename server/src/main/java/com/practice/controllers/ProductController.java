package com.practice.controllers;

import com.practice.dtos.CreateProductDto;
import com.practice.models.Product;
import com.practice.repositories.ProductRepository;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class ProductController {
    
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    // get all products
    public void getAll(Context ctx) {
        ctx.json(productRepository.findAll());
    }

    // add a new product
    public void create(Context ctx) {
        CreateProductDto dto = ctx.bodyAsClass(CreateProductDto.class);
        Product product = new Product(null, dto.categoryId(), dto.name(), dto.price());
        
        Integer id = productRepository.addProduct(product);
        ctx.status(HttpStatus.CREATED).json(id);
    }

    // update a product
    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        CreateProductDto dto = ctx.bodyAsClass(CreateProductDto.class);
        Product product = new Product(id, dto.categoryId(), dto.name(), dto.price());

        boolean updated = productRepository.updateProduct(product);
        if (updated) {
            ctx.status(HttpStatus.NO_CONTENT);
        } else {
            ctx.status(HttpStatus.NOT_FOUND).result("Product not found");
        }
    }
}
