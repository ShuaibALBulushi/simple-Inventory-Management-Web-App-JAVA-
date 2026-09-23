package com.practice.controllers;

import com.practice.dtos.CreateInventoryDto;
import com.practice.models.Inventory;
import com.practice.repositories.InventoryRepository;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class InventoryController {
    
    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    // get all inventory items
    public void getAll(Context ctx) {
        ctx.json(inventoryRepository.findAll());
    }
}
