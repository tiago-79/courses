package br.gov.caixa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseRequest(
        @NotNull
        @NotBlank
        @Size(min = 3, message = "O nome do curso deve ter no mínimo 3 caracteres.")
        String name ) {
}
