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

    // add a new order
    public void create(Context ctx) {
        CreateOrderDto dto = ctx.bodyAsClass(CreateOrderDto.class);
        Order order = new Order(null, null, dto.status());
        
        Integer id = orderRepository.addOrder(order);
        ctx.status(HttpStatus.CREATED).json(id);
    }
}
