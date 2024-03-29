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

@Path("server/{serverId:\\d+}/category/{categoryId:\\d+}/channel")
@ApplicationScoped
@Authenticated
public class CategoryResource {

    @PathParam("serverId")
    Long serverId;

    @PathParam("categoryId")
    Long categoryId;

    @Inject
    UserService us;

    @Inject
    EntityManager entityManager;

    @Inject
    ServerService serverService;

    @Inject
    CategoryService categoryService;

    @Inject
    ServerChannelService channelService;

    @Inject
    MessageService messageService;

    @GET
    @Path("/{channelId:\\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getChannel(@PathParam("channelId") Long channelId) {
        ServerChannel chan = channelService.get(channelId);

        return API.createMessagesResponse("Get channel success", chan);
    }

    @POST
    @Path("/{channelId:\\d+}/send")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonObject postMessage(@Context SecurityContext securityContext, @PathParam("channelId") Long channelId, @FormParam("text") String text) {
        ServerChannel channel = channelService.get(channelId);
        Account account = us.resolve(securityContext);

        Message message = messageService.create(channel, account, text);

        return API.createResponse("Send message success", message);
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonObject createChannel(@Context SecurityContext securityContext, @FormParam("channelName") String channelName) {
        Account account = us.resolve(securityContext);

        Server server = serverService.get(serverId);

        if(!server.getOwner().getId().equals(account.getId())) {
            return API.createErrorResponse("Error, can't create channel");
        }

        Category category = categoryService.get(categoryId);
        
        ServerChannel channel = channelService.create(category, channelName);

        return API.createResponse("Create channel success", channel);
    }
}
