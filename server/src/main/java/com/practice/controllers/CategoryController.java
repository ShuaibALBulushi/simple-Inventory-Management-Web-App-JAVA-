package com.practice.controllers;

import com.practice.dtos.CreateCategoryDto;
import com.practice.models.Category;
import com.practice.repositories.CategoryRepository;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    // find all categories
    public void getAll(Context ctx) {
        ctx.json(categoryRepository.findAll());
    }

    // add a new category
    public void create(Context ctx){
        
        CreateCategoryDto dto = ctx.bodyAsClass(CreateCategoryDto.class);
        Category category = new Category(null, dto.name());

        Integer id = categoryRepository.addCategory(category);
        ctx.status(HttpStatus.CREATED).json(id);
    }
}
