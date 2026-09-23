package com.practice.controllers;

import com.practice.dtos.CreateOrderDto;
import com.practice.models.Order;
import com.practice.repositories.OrderRepository;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class OrderController {
    
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // get all orders
    public void getAll(Context ctx) {
        ctx.json(orderRepository.findAll());
    }
}
