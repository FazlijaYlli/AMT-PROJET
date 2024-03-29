package ch.heigvd.resource;

import ch.heigvd.entities.*;
import ch.heigvd.service.*;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Path("server/{serverId:\\d+}")
@ApplicationScoped
@Authenticated
public class ServerResource {

    @PathParam("serverId")
    Long serverId;

    @Inject
    UserService us;

    @Inject
    EntityManager entityManager;

    @Inject
    ServerService serverService;

    @Inject
    CategoryService categoryService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getServer() {
        Server srv = serverService.get(serverId);

        return API.createCategoryAndChannelListResponse("Server info", srv);
    }

    @POST
    @Path("/category/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonObject createCategory(@Context SecurityContext securityContext, @FormParam("categoryName") String categoryName) {
        Account account = us.resolve(securityContext);

        Server server = serverService.get(serverId);

        if(!server.getOwner().getId().equals(account.getId())) {
            return API.createErrorResponse("Error, can't create channel");
        }

        Server srv = serverService.get(serverId);

        Category category = categoryService.create(srv, categoryName);

        return API.createResponse("Category created", category);
    }
}
