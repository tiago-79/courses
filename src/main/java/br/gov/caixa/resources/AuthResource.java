package br.gov.caixa.resources;

import br.gov.caixa.dto.AuthRequest;
import br.gov.caixa.dto.AuthResponse;
import br.gov.caixa.dto.SignInResponse;
import br.gov.caixa.dto.SignUpRequest;
import br.gov.caixa.model.UserAuth;
import br.gov.caixa.security.JwtGenerator;
import br.gov.caixa.services.UserService;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.smallrye.common.constraint.NotNull;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    JwtGenerator jwtGenerator;

    @Inject
    UserService userService;

    @Inject

    public AuthResource(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @POST
    @Path("/sign-up")
    public Response signUp(SignUpRequest request) {

        Optional<UserAuth> existing = UserAuth.find("email", request.email()).firstResultOptional();
        if (existing.isPresent()) {
            return Response.status(Response.Status.CONFLICT) // Retorna 409
                    .entity("Email já cadastrado")
                    .build();
        }
        QuarkusTransaction.requiringNew()
                .run(() -> {
                    UserAuth.add(
                            request.username(),
                            request.email(),
                            request.password(),
                            "USER"
                    );
                });
        return Response.ok().build();
    }

    @POST
    @Path("/sign-in")
    public Response signIn(SignUpRequest request) {

        Optional<UserAuth> possibleUser = UserAuth.find("email", request.email())
                .firstResultOptional();

        Duration expiresIn = Duration.ofHours(1);
        if (possibleUser.isPresent()) {
            UserAuth userAuth = possibleUser.get();
            String token = jwtGenerator.generateJws(
                    userAuth.getEmail(), Set.of("USER"), expiresIn
            );
            return Response.ok(new SignInResponse(
                    token,expiresIn.toMillis()
            )).build();
        }

        return Response.status(Response.Status.FORBIDDEN).build();
    }

//    @POST
//    @Path("/jws")
//    public Response generateJws() {
//        return Response.ok(token).build();
//        //String token = jwtGenerator.generateJws(userAuth.getUserName(), userAuth.getRole());
//    }

    @POST
    @Path("/jwe")
    public Response generateJwe() {

        String token = jwtGenerator.generateJwe();

        return Response.ok(token).build();
    }

    @POST
    @Path("/token")
    public Response token(@Valid @NotNull AuthRequest request) {

        UserAuth userAuth = userService.authenticate(request.email(), request.password());
        Duration expiresIn = Duration.ofHours(1);

        if (userAuth == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = Jwt.issuer("courses-api")
                .subject(userAuth.getEmail())
                .groups(Set.of(userAuth.getRole()))
                .expiresIn(Duration.ofSeconds(expiresIn.toMillis()))
                .sign();

        return Response.ok(new AuthResponse(expiresIn, token)).build();
    }

}
