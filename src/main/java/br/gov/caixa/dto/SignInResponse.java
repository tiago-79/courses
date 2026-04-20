package br.gov.caixa.dto;

public record SignInResponse(
        String token,
        Long expiresIn
) {

}
