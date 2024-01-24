package ch.heigvd.resource;

import ch.heigvd.entities.Account;
import ch.heigvd.entities.Server;
import ch.heigvd.service.API;
import ch.heigvd.service.ServerService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;

@Path("servers")
@ApplicationScoped
public class ServersResource {

    @Inject
    EntityManager entityManager;

    @GET
    @Path("")
    @Authenticated
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getServers(@Context SecurityContext securityContext) {

        ServerService serverService = new ServerService();

        List<Server> servers = serverService.listServers();

        String successMessage = "List of servers for user " + ((Account)securityContext.getUserPrincipal()).getUsername();

        return API.createServerListResponse(successMessage, servers);
    }
    
}
