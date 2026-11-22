package com.deliveryapp.backend.order.model;

import com.deliveryapp.backend.order.enums.EOrderStatus;
import com.deliveryapp.backend.order.enums.EPaymentType;
import com.deliveryapp.backend.store.model.Store;
import com.deliveryapp.backend.user.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rider_id")
    private User rider;

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private User consumer;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "customer_address", nullable = false, length = 50)
    private String customerAddress;

    @Column(name = "customer_latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal customerLatitude;

    @Column(name = "customer_longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal customerLongitude;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    @Column(name = "total", nullable = false, precision = 9, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @Enumerated(EnumType.STRING)
    private EPaymentType paymentType;

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    @PrePersist
    @PreUpdate
    private void linkItemsToOrder() {
        if (items != null) {
            items.forEach(item -> item.setOrder(this));
        }
    }
}
