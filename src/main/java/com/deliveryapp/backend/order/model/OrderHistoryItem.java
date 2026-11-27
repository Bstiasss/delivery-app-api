package com.deliveryapp.backend.order.model;

import java.math.BigDecimal;

public record OrderHistoryItem(
        String productName,
        BigDecimal price,
        int quantity
) {}
