package com.deliveryapp.backend.order.service;

import com.deliveryapp.backend.common.pagination.PaginationQuery;
import com.deliveryapp.backend.common.pagination.PaginationResult;
import com.deliveryapp.backend.common.services.AuthFacadeService;
import com.deliveryapp.backend.order.dto.CreateOrderRequestDTO;
import com.deliveryapp.backend.order.dto.OrderResponseDTO;
import com.deliveryapp.backend.order.dto.UpdateOrderLocationRequestDTO;
import com.deliveryapp.backend.order.dto.UpdateOrderStatusRequestDTO;
import com.deliveryapp.backend.order.enums.EOrderStatus;
import com.deliveryapp.backend.order.enums.EPaymentType;
import com.deliveryapp.backend.order.exception.InvalidOrderStatusException;
import com.deliveryapp.backend.order.exception.OrderNotFoundException;
import com.deliveryapp.backend.order.filter.AdminOrderFilter;
import com.deliveryapp.backend.order.filter.OrderFilter;
import com.deliveryapp.backend.order.mapper.OrderMapper;
import com.deliveryapp.backend.order.model.Order;
import com.deliveryapp.backend.order.repository.OrderRepository;
import com.deliveryapp.backend.order.specification.OrderSpecification;
import com.deliveryapp.backend.product.enums.EProductStatus;
import com.deliveryapp.backend.product.exception.InvalidParameterSortByException;
import com.deliveryapp.backend.product.exception.ProductException;
import com.deliveryapp.backend.product.exception.ProductNotFoundException;
import com.deliveryapp.backend.product.model.Product;
import com.deliveryapp.backend.product.repository.ProductRepository;
import com.deliveryapp.backend.store.model.Store;
import com.deliveryapp.backend.user.enums.ERole;
import com.deliveryapp.backend.user.exception.UserNotAuthorizedException;
import com.deliveryapp.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.deliveryapp.backend.store.repository.StoreRepository;
import com.deliveryapp.backend.store.exception.StoreNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.deliveryapp.backend.order.dto.OrderItemRequestDTO;
import com.deliveryapp.backend.order.model.OrderItem;
import java.util.HashSet;
import java.util.Set;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AuthFacadeService authFacadeService;
    private final StoreRepository storeRepository;

    @Override
    @Transactional(readOnly = true)
    public PaginationResult<OrderResponseDTO> findByStoreId(Long storeId, PaginationQuery paginationQuery, OrderFilter orderFilter) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() ->
                        new StoreNotFoundException(storeId)
                );

        if (!store.getOwner().equals(authFacadeService.getCurrentUser())) {
            throw new StoreNotFoundException(storeId);
        }

        if (!(paginationQuery.getDirection().equalsIgnoreCase("asc")
                || paginationQuery.getDirection().equalsIgnoreCase("desc"))) {

            throw new InvalidParameterSortByException(
                    "Parametro direction solo acepta valores: asc , desc"
            );
        }

        if (!(paginationQuery.getSortBy().equalsIgnoreCase("id")
                || paginationQuery.getSortBy().equalsIgnoreCase("total")
                || paginationQuery.getSortBy().equalsIgnoreCase("status")
                || paginationQuery.getSortBy().equalsIgnoreCase("paymentType")
                || paginationQuery.getSortBy().equalsIgnoreCase("lastUpdate"))) {

            throw new InvalidParameterSortByException(
                    "Parametro sortBy solo acepta valores: id , total, status, paymentType, lastUpdate"
            );
        }

        PageRequest pageRequest = PageRequest.of(
                paginationQuery.getPage(),
                paginationQuery.getSize(),
                Sort.by(
                        Sort.Direction.fromString(
                                paginationQuery.getDirection()
                        ),
                        paginationQuery.getSortBy()
                )
        );

        Specification<Order> specification = Specification.allOf(
                OrderSpecification.byStoreId(storeId)
                        .and(
                                OrderSpecification.byStatus(
                                                orderFilter.getStatus() == null
                                                        ? null
                                                        : EOrderStatus.valueOf(orderFilter.getStatus())
                                        )
                                        .and(
                                                OrderSpecification.byPaymentType(
                                                                orderFilter.getPaymentType() == null
                                                                        ? null
                                                                        : EPaymentType.valueOf(orderFilter.getPaymentType()))
                                                        .and(
                                                                OrderSpecification.byConsumerId(orderFilter.getConsumerId())
                                                                        .and(
                                                                                OrderSpecification.byTotal(
                                                                                        orderFilter.getTotalMin(),
                                                                                        orderFilter.getTotalMax()
                                                                                )
                                                                        )
                                                        )
                                        )
                        )
        );

        Page<Order> page = orderRepository.findAll(specification, pageRequest);

        return new PaginationResult<>(
                page.getContent()
                        .stream()
                        .map(OrderMapper::toResponse)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResult<OrderResponseDTO> findAll(
            PaginationQuery paginationQuery,
            AdminOrderFilter orderFilter
    ) {
        if (!authFacadeService.isRole(ERole.ROLE_ADMINISTRATOR)) {
            throw new RuntimeException(
                    "Solo los administradores pueden consultar todos los pedidos"
            );
        }

        if (!(paginationQuery.getDirection().equalsIgnoreCase("asc")
                || paginationQuery.getDirection().equalsIgnoreCase("desc"))) {

            throw new InvalidParameterSortByException(
                    "Parametro direction solo acepta valores: asc , desc"
            );
        }

        if (!(paginationQuery.getSortBy().equalsIgnoreCase("id")
                || paginationQuery.getSortBy().equalsIgnoreCase("total")
                || paginationQuery.getSortBy().equalsIgnoreCase("status")
                || paginationQuery.getSortBy().equalsIgnoreCase("paymentType")
                || paginationQuery.getSortBy().equalsIgnoreCase("lastUpdate")
                || paginationQuery.getSortBy().equalsIgnoreCase("customerAddress"))) {

            throw new InvalidParameterSortByException(
                    "Parametro sortBy solo acepta valores: id, total, status, paymentType, lastUpdate, customerAddress"
            );
        }
        
        PageRequest pageRequest = PageRequest.of(
                paginationQuery.getPage(),
                paginationQuery.getSize(),
                Sort.by(
                        Sort.Direction.fromString(
                                paginationQuery.getDirection()
                        ),
                        paginationQuery.getSortBy()
                )
        );

        Specification<Order> specification = Specification.allOf(
                OrderSpecification.byId(orderFilter.getId())
                        .and(OrderSpecification.byRiderId(orderFilter.getRiderId()))
                        .and(OrderSpecification.byConsumerId(orderFilter.getConsumerId()))
                        .and(OrderSpecification.byStoreId(orderFilter.getStoreId()))
                        .and(OrderSpecification.byCustomerAddress(orderFilter.getCustomerAddress()))
                        .and(OrderSpecification.byStatus(orderFilter.getStatus()))
                        .and(OrderSpecification.byPaymentType(orderFilter.getPaymentType()))
                        .and(OrderSpecification.byTotal(
                                orderFilter.getTotalMin(),
                                orderFilter.getTotalMax()
                        ))
                        .and(OrderSpecification.byLastUpdate(
                                orderFilter.getLastUpdateFrom(),
                                orderFilter.getLastUpdateTo()
                        ))
        );

        Page<Order> page =
                orderRepository.findAll(specification, pageRequest);

        return new PaginationResult<>(
                page.getContent()
                        .stream()
                        .map(OrderMapper::toResponse)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    @Override
    @Transactional
    public OrderResponseDTO save(CreateOrderRequestDTO dto) {

        List<OrderItem> items = prepararItems(dto);

        Store store = items.getFirst().getProduct().getStore();

        BigDecimal total = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = OrderMapper.toEntity(
                dto,
                authFacadeService.getCurrentUser(),
                store,
                total,
                items,
                EOrderStatus.PENDING,
                null
        );

        Order newOrder = orderRepository.save(order);

        return OrderMapper.toResponse(newOrder);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OrderResponseDTO> findById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        var currentUser = authFacadeService.getCurrentUser();

        boolean isRider = currentUser.equals(order.getRider());
        boolean isConsumer = currentUser.equals(order.getConsumer());
        boolean isStoreOwner = currentUser.equals(order.getStore().getOwner());
        boolean isAdmin = currentUser.getRole().equals(ERole.ROLE_ADMINISTRATOR);

        if (!isRider && !isConsumer && !isStoreOwner && !isAdmin) {
            throw new OrderNotFoundException(id);
        }

        return Optional.of(OrderMapper.toResponse(order));
    }

    @Override
    @Transactional
    public OrderResponseDTO update(Long id, CreateOrderRequestDTO dto) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        User currentUser = authFacadeService.getCurrentUser();

        if (!order.getConsumer().equals(currentUser)) {
            throw new RuntimeException("No tenés permiso para modificar este pedido");
        }

        if (order.getStatus() != EOrderStatus.PENDING) {
            throw new RuntimeException("Solo se pueden modificar pedidos pendientes");
        }

        List<OrderItem> newItems = prepararItems(dto);

        Store store = newItems.getFirst().getProduct().getStore();

        if (!store.getId().equals(order.getStore().getId())) {
            throw new RuntimeException("No se puede cambiar el comercio de un pedido");
        }

        BigDecimal total = newItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.getItems().clear();

        newItems.forEach(order::addItem);

        order.setCustomerAddress(
                dto.getCustomerAddress() == null || dto.getCustomerAddress().isBlank()
                        ? "Dirección no especificada"
                        : dto.getCustomerAddress().trim()
        );

        order.setTotal(total);
        order.setPaymentType(dto.getPaymentType());

        Order updatedOrder = orderRepository.save(order);

        return OrderMapper.toResponse(updatedOrder);
    }

    @Override
    public OrderResponseDTO updateMerchantOrderStatus(
            Long id,
            UpdateOrderStatusRequestDTO dto
    ) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(id)
                );

        User currentUser = authFacadeService.getCurrentUser();

        if (!order.getStore().getOwner().equals(currentUser)) {

            throw new StoreNotFoundException(currentUser.getId());
        }

        EOrderStatus currentStatus = order.getStatus();
        EOrderStatus newStatus = dto.getStatus();

        if ((currentStatus == EOrderStatus.PENDING && (newStatus == EOrderStatus.CONFIRMED || newStatus == EOrderStatus.CANCELLED))
                || (currentStatus == EOrderStatus.CONFIRMED_ASSIGNED && newStatus == EOrderStatus.DISPATCHED)) {

            order.setStatus(newStatus);

            Order updatedOrder = orderRepository.save(order);

            return OrderMapper.toResponse(updatedOrder);
        } else {
            throw new InvalidOrderStatusException(
                    "Operación inválida"
            );
        }
    }

    @Override
    public OrderResponseDTO updateRiderOrderStatus(
            Long id,
            UpdateOrderStatusRequestDTO dto) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(id)
                );

        User currentUser = authFacadeService.getCurrentUser();
        if (!currentUser.getRole().equals(ERole.ROLE_RIDER)) {
            throw new UserNotAuthorizedException("Operación prohibida");
        }

        EOrderStatus currentStatus = order.getStatus();
        EOrderStatus newStatus = dto.getStatus();

        if ((currentStatus == EOrderStatus.CONFIRMED && newStatus == EOrderStatus.CONFIRMED_ASSIGNED)
                || (currentStatus == EOrderStatus.DISPATCHED && newStatus == EOrderStatus.COMPLETED)) {

            order.setStatus(newStatus);
            order.setRider(authFacadeService.getCurrentUser());
            Order updatedOrder = orderRepository.save(order);

            return OrderMapper.toResponse(updatedOrder);
        } else {
            throw new InvalidOrderStatusException(
                    "Operación inválida"
            );
        }

    }

    @Override
    public OrderResponseDTO updateOrderLocation(
            Long id,
            UpdateOrderLocationRequestDTO dto
    ) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(id)
                );

        order.setLatitude(dto.getLatitude());

        order.setLongitude(dto.getLongitude());

        Order updatedOrder = orderRepository.save(order);

        return OrderMapper.toResponse(updatedOrder);
    }

    @Override
    public void deleteById(Long id) {

        orderRepository.findById(id)
                .orElseThrow(
                        () -> new OrderNotFoundException(id)
                );

        orderRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findUnassignedOrders() {
        return orderRepository.findByRiderIsNullAndStatus(EOrderStatus.CONFIRMED)
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findMyOrders() {
        if (authFacadeService.getCurrentUser().getRole().equals(ERole.ROLE_CONSUMER)) {
            Long consumerId = authFacadeService.getCurrentUser().getId();
            return orderRepository.findByConsumerId(consumerId)
                    .stream()
                    .map(OrderMapper::toResponse)
                    .toList();
        } else {
            Long riderId = authFacadeService.getCurrentUser().getId();
            return orderRepository.findByRiderId(riderId)
                    .stream()
                    .map(OrderMapper::toResponse)
                    .toList();

        }
    }

    private List<OrderItem> prepararItems(CreateOrderRequestDTO dto) {

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new RuntimeException("Debe haber al menos un producto");
        }

        List<OrderItem> items = new ArrayList<>();
        Set<Long> productIds = new HashSet<>();

        Store store = null;

        for (OrderItemRequestDTO itemDTO : dto.getItems()) {

            if (itemDTO.getQuantity() == null || itemDTO.getQuantity() < 1) {
                throw new RuntimeException("La cantidad debe ser como mínimo 1");
            }

            if (!productIds.add(itemDTO.getProductId())) {
                throw new RuntimeException("No se puede repetir un producto. Debe indicar su cantidad en una sola línea");
            }

            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemDTO.getProductId()));

            if (product.getStatus() == EProductStatus.UNAVAILABLE) {
                throw new ProductException(
                        "El producto " + product.getName() + " no está disponible"
                );
            }

            if (store == null) {
                store = product.getStore();
            } else if (!store.getId().equals(product.getStore().getId())) {
                throw new RuntimeException(
                        "Todos los productos del pedido deben pertenecer al mismo comercio"
                );
            }

            items.add(OrderItem.builder()
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .unitPrice(product.getPrice())
                    .build());
        }

        return items;
    }
}
