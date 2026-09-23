package com.practice.controllers;

import com.practice.dtos.CreateOrderItemDto;
import com.practice.models.OrderItem;
import com.practice.repositories.OrderItemRepository;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class OrderItemController {
    
    private final OrderItemRepository orderItemRepository;

    public OrderItemController(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    // get all order items
    public void getAll(Context ctx) {
        ctx.json(orderItemRepository.findAll());
    }

    // add a new order item
    public void create(Context ctx) {
        CreateOrderItemDto dto = ctx.bodyAsClass(CreateOrderItemDto.class);
        OrderItem item = new OrderItem(null, dto.orderId(), dto.productId(), dto.quantity(), dto.unitPrice());
        
        Integer id = orderItemRepository.addOrderItem(item);
        ctx.status(HttpStatus.CREATED).json(id);
    }
}
