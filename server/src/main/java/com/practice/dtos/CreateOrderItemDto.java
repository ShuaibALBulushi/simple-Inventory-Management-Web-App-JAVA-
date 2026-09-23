package com.practice.dtos;

import java.math.BigDecimal;

public record CreateOrderItemDto(
    Integer orderId,
    Integer productId,
    Integer quantity,
    BigDecimal unitPrice
) {}
