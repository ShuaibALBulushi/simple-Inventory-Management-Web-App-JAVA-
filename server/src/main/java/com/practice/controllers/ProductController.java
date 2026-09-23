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
    
}
