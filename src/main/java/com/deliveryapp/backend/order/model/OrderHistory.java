package com.deliveryapp.backend.order.model;

import com.deliveryapp.backend.order.enums.EOrderStatus;
import com.deliveryapp.backend.order.enums.EPaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "orderHistory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rider_id")
    private Long riderId;

    @Column(name = "rider_name", nullable = false, length = 20)
    private String riderName;

    @Column(name = "rider_last_name", nullable = false, length = 30)
    private String riderLastName;

    @Column(name = "rider_email", unique = true, nullable = false, length = 100)
    private String riderEmail;

    @Column(name = "consumer_id")
    private Long consumerId;

    @Column(name = "consumer_name", nullable = false, length = 20)
    private String consumerName;

    @Column(name = "consumer_last_name", nullable = false, length = 30)
    private String consumerLastName;

    @Column(name = "consumer_email", unique = true, nullable = false, length = 100)
    private String consumerEmail;

    @Column(name = "customer_address", nullable = false, length = 50)
    private String customerAddress;

    @Column(name = "customer_latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal customerLatitude;

    @Column(name = "customer_longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal customerLongitude;

    @Column(name = "merchant_id")
    private Long merchantId;

    @Column(name = "merchant_name", nullable = false, length = 20)
    private String merchantName;

    @Column(name = "merchant_last_name", nullable = false, length = 30)
    private String merchantLastName;

    @Column(name = "merchant_email", unique = true, nullable = false, length = 100)
    private String merchantEmail;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "store_name", nullable = false, length = 25)
    private String storeName;

    @Column(name = "store_address", nullable = false, length = 40)
    private String storeAddress;

    @Column(name = "store_latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal storeLatitude;

    @Column(name = "store_longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal storeLongitude;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json") // o "json" según el motor
    private List<OrderHistoryItem> items;

    @Column(name = "total", nullable = false, precision = 9, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private EPaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

}
