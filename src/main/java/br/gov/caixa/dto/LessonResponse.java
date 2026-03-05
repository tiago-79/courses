package br.gov.caixa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LessonResponse(
        Long id,
        @NotNull
        @NotBlank
        String name ) {
}
