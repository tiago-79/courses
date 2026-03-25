package br.gov.caixa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotNull
        @NotBlank
        String name,

        @NotNull
        @NotBlank
        String email,

        @NotNull
        @NotBlank
        @Size(min = 3, message = "A senha(password) deve ter no mínimo 8 caracteres.")
        String password) {
}
