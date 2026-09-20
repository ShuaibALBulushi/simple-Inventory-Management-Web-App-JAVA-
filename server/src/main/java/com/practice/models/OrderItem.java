package com.practice.models;

import java.math.BigDecimal;

public record OrderItem(
    Integer id,
    Integer orderId,
    Integer productId,
    Integer quantity,
    BigDecimal unitPrice
) {}