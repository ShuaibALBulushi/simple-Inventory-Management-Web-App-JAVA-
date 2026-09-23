package com.practice.dtos;

import java.math.BigDecimal;

public record CreateProductDto(
    Integer categoryId,
    String name,
    BigDecimal price
) {}
