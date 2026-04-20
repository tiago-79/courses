package br.gov.caixa.resources;

import br.gov.caixa.dto.UserRequest;
import br.gov.caixa.dto.UserResponse;
import br.gov.caixa.model.UserAuth;
import br.gov.caixa.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;
import java.util.Map;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    JsonWebToken jwt;

    private final UserService userService;
    public UserResource(UserService service){
        this.userService = service;
    }

    @Context
    UriInfo uriInfo;

    @POST
    @Transactional
    @RolesAllowed({"ADMIN"})
    public Response createUser(@Valid UserRequest userRequest){
        if (userService.findByEmail(userRequest.email()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("message", "E-mail já cadastrado no sistema."))
                    .build();
        }
        UserAuth newUserAuth = new UserAuth(userRequest.username(), userRequest.email(), userRequest.password());

        this.userService.createUser(newUserAuth);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(newUserAuth.id.toString())
                .build();

        UserResponse payload = new UserResponse(newUserAuth.id, newUserAuth.getUsername(), newUserAuth.getEmail(), newUserAuth.getPassword());
        return Response.created(uri).entity(payload).build();
    }

    @GET
    @Path("/me")
    @RolesAllowed({"USER", "ADMIN"})
    public Response me() {
        UserAuth userAuth = userService.findByEmail(jwt.getSubject());

        if (userAuth == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(Map.of(
                "id", userAuth.id,
                "name", userAuth.getUsername(),
                "email", userAuth.getEmail(),
                "role", userAuth.getRole()
        )).build();
    }
}
