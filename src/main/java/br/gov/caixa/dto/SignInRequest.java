package br.gov.caixa.dto;

public record SignInRequest(
        String email, String password
) {
}
