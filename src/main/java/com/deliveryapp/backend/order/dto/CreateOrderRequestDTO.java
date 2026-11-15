package com.deliveryapp.backend.order.dto;

import com.deliveryapp.backend.order.enums.EPaymentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Debe seleccionar un método de pago")
    private EPaymentType paymentType;
}
