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

    // add a new inventory item
    public void create(Context ctx) {
        CreateInventoryDto dto = ctx.bodyAsClass(CreateInventoryDto.class);
        Inventory inventory = new Inventory(null, dto.productId(), dto.quantity());
        
        Integer id = inventoryRepository.addInventory(inventory);
        ctx.status(HttpStatus.CREATED).json(id);
    }
}
