package br.gov.caixa.dto;

import java.time.Duration;

public record AuthResponse (Duration expiresIn, String token) {
}
