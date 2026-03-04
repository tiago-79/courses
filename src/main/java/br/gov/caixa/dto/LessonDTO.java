package br.gov.caixa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LessonDTO(
        @NotNull
        @NotBlank
        String name ) {
}
