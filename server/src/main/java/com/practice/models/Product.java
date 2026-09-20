package com.practice.models;

import java.math.BigDecimal;

public record Product(
    Integer id,
    String name,
    String sku,
    BigDecimal price,
    Integer categoryId
) {}
