package com.deliveryapp.backend.order.service;

import com.deliveryapp.backend.order.model.OrderHistory;
import com.deliveryapp.backend.order.repository.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderHistoryService implements IOrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;

    @Override
    public void saveOrderHistory(OrderHistory orderHistory) {
        orderHistoryRepository.save(orderHistory);
    }
}
