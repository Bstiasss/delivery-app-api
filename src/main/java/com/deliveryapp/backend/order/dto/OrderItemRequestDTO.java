package com.deliveryapp.backend.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDTO {

    @NotNull(message = "Debe indicar el producto")
    private Long productId;

    @NotNull(message = "Debe indicar la cantidad")
    @Min(value = 1, message = "La cantidad debe ser como mínimo 1")
    private Integer quantity;
}