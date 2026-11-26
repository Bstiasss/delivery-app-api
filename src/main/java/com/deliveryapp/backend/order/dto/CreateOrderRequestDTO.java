package com.deliveryapp.backend.order.dto;

import com.deliveryapp.backend.order.enums.EPaymentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequestDTO {

    @Size(max = 50, message = "La dirección no puede superar los 50 caracteres")
    private String customerAddress;

    @NotEmpty(message = "Debe haber mínimo un producto")
    @Valid
    private List<OrderItemRequestDTO> items;

    @NotNull(message = "La latitud del consumidor no puede ser nulo")
    @DecimalMin(value = "-90.0", message = "La latitud del consumidor debe ser mayor a -90")
    @DecimalMax(value = "90.0", message = "La latitud del consumidor debe ser menor a 90")
    @Digits(integer = 3, fraction = 7, message = "La latitud del consumidor debe tener hasta 7 decimales")
    private BigDecimal customerLatitude;

    @NotNull(message = "La longitud del consumidor no puede ser nulo")
    @DecimalMin(value = "-180.0", message = "La longitud  del consumidor debe ser mayor a -180")
    @DecimalMax(value = "180.0", message = "La longitud  del consumidor debe ser menor a 180")
    @Digits(integer = 3, fraction = 7, message = "La longitud del consumidor debe tener hasta 7 decimales")
    private BigDecimal customerLongitude;

    @NotNull(message = "Debe seleccionar un método de pago")
    private EPaymentType paymentType;
}
