package com.deliveryapp.backend.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 20, message = "El nombre no puede superar los 20 caracteres")
    private String name;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 30, message = "El apellido no puede superar los 30 caracteres")
    private String lastName;

    @Size(min=6, message = "La contraseña debe tener al menos 6 caracteres")
    @Size(max = 20, message = "La contraseña no puede superar los 20 caracteres")
    private String password;

}
