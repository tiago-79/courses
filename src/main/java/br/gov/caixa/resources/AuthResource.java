package br.gov.caixa.resources;

import br.gov.caixa.security.JwtGenerator;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    @Inject
    JwtGenerator jwtGenerator;

    @POST
    @Path("/jws")
    public Response generateJws(){

        String token = jwtGenerator.generateJws();

        return Response.ok(token).build();
    }
}
