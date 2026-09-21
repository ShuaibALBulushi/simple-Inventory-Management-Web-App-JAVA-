package com.practice.models;

import java.math.BigDecimal;

public record Product(
    Integer id, 
    Integer categoryId, 
    String name, 
    BigDecimal price
) {}
