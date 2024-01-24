package ch.heigvd.service;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import io.quarkus.security.AuthenticationFailedException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFailureHandler implements ExceptionMapper<AuthenticationFailedException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(AuthenticationFailedException exception) {
        return Response
                .status(Response.Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(API.createErrorResponse("Auth failed").toString())
                .build();
    }
}