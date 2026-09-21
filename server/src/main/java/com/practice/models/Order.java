package com.practice.models;

import java.time.LocalDateTime;

public record Order(
    Integer id, 
    LocalDateTime orderDate, 
    String status
) {}
