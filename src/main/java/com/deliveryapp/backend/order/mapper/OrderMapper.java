package com.deliveryapp.backend.order.mapper;

import com.deliveryapp.backend.order.dto.CreateOrderRequestDTO;
import com.deliveryapp.backend.order.dto.OrderItemResponseDTO;
import com.deliveryapp.backend.order.dto.OrderResponseDTO;
import com.deliveryapp.backend.order.enums.EOrderStatus;
import com.deliveryapp.backend.order.model.Order;
import com.deliveryapp.backend.order.model.OrderItem;
import com.deliveryapp.backend.store.model.Store;
import com.deliveryapp.backend.user.model.User;

import java.math.BigDecimal;
import java.util.List;

public class OrderMapper {

    public static OrderResponseDTO toResponse(Order order) {

        List<OrderItemResponseDTO> items = order.getItems()
                .stream()
                .map(item -> OrderItemResponseDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .toList();

        List<Long> products = order.getItems()
                .stream()
                .map(item -> item.getProduct().getId())
                .toList();

        return OrderResponseDTO.builder()
                .id(order.getId())
                .riderId(order.getRider() != null
                        ? order.getRider().getId()
                        : null)
                .storeId(order.getStore().getId())
                .consumerId(order.getConsumer().getId())
                .products(products)
                .items(items)
                .customerAddress(order.getCustomerAddress())
                .customerLatitude(order.getCustomerLatitude())
                .customerLongitude(order.getCustomerLongitude())
                .total(order.getTotal())
                .latitude(order.getLatitude())
                .longitude(order.getLongitude())
                .status(order.getStatus())
                .paymentType(order.getPaymentType())
                .storeName(order.getStore().getName())
                .consumerName(
                        order.getConsumer().getName() + " " +
                                order.getConsumer().getLastName()
                )
                .riderName(
                        order.getRider() != null
                                ? order.getRider().getName() + " " +
                                order.getRider().getLastName()
                                : null
                )
                .storeAddress(order.getStore().getAddress())
                .customerAddress(order.getCustomerAddress())
                .build();
    }

    public static Order toEntity(
            CreateOrderRequestDTO dto,
            User consumer,
            Store store,
            BigDecimal total,
            List<OrderItem> items,
            EOrderStatus status,
            User rider
    ) {

        Order order = Order.builder()
                .customerAddress(dto.getCustomerAddress() == null
                        || dto.getCustomerAddress().isBlank()
                        ? "Dirección no especificada"
                        : dto.getCustomerAddress().trim())
                .total(total)
                .status(status)
                .consumer(consumer)
                .rider(rider)
                .store(store)
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .customerLatitude(dto.getCustomerLatitude())
                .customerLongitude(dto.getCustomerLongitude())
                .paymentType(dto.getPaymentType())
                .build();

        items.forEach(order::addItem);

        return order;
    }
}