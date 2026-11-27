package com.deliveryapp.backend.order.service;

import com.deliveryapp.backend.order.model.OrderHistory;

public interface IOrderHistoryService {
    void saveOrderHistory(OrderHistory orderHistory);
}
