package com.mtrxxp.backend.model.dto;

public record OrderItemRequest(
        int productId,
        int quantity
) {}
