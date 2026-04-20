package br.gov.caixa.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class JwtGenerator {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public String generateJws(String username, Set<String> roles, Duration expiresIn) {

        return Jwt.claim(Claims.sub, username)
                .expiresIn(expiresIn)
                .issuer(issuer)
                .groups(roles)
                //.claim(Claims.email_verified, true)
                .sign();
    }

    public String generateJwe() {

        return Jwt.claim(Claims.sub, UUID.randomUUID().toString())
                .claim(Claims.email_verified, true)
                .jwe()
                .encrypt();
    }

}
