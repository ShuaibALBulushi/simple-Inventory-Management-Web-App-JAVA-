package com.practice.models;

public record Inventory(
    Integer id, 
    Integer productId, 
    Integer quantity
) {}