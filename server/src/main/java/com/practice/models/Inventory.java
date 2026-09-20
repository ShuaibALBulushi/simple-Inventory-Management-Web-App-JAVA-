package com.practice.models;

import java.time.LocalDateTime;

public record Inventory(
    Integer id,
    Integer productId,
    Integer quantity,
    LocalDateTime lastUpdated
) {}