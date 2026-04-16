package br.gov.caixa.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;

import java.util.UUID;

@ApplicationScoped
public class JwtGenerator {

    public String generateJws(){

        return Jwt.claim(Claims.sub, UUID.randomUUID().toString())
                .claim(Claims.email_verified, true)
                .sign();
    }

    public String generateJwe(){

        return Jwt.claim(Claims.sub, UUID.randomUUID().toString())
                .claim(Claims.email_verified, true)
                .jwe()
                .encrypt();
    }

}
