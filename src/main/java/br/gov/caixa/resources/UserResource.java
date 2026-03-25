package br.gov.caixa.resources;

import br.gov.caixa.dto.UserRequest;
import br.gov.caixa.dto.UserResponse;
import br.gov.caixa.model.User;
import br.gov.caixa.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService userService;

    public UserResource(UserService service){
        this.userService = service;
    }
    @Context
    UriInfo uriInfo;

    @POST
    @Transactional
    public Response createUser(@Valid UserRequest userRequest){

        User newUser = new User(userRequest.name(), userRequest.email(), userRequest.password());
        this.userService.createUser(newUser);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(newUser.id.toString())
                .build();

        UserResponse payload = new UserResponse(newUser.id,newUser.getName(), newUser.getEmail());
        return Response.created(uri).entity(payload).build();
    }
}
