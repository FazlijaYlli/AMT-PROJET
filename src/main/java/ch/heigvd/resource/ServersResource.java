package ch.heigvd.resource;

import ch.heigvd.entities.*;
import ch.heigvd.service.API;
import ch.heigvd.service.ServerService;
import ch.heigvd.service.UserService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

import java.util.Collections;
import java.util.List;

@Path("servers")
@ApplicationScoped
@Authenticated
public class ServersResource {

    @Inject
    UserService us;

    @Inject
    EntityManager entityManager;


    @Inject
    ServerService serverService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getServers() {
        List<Server> servers = serverService.listServers();

        String successMessage = "List of all servers";

        return API.createServerListResponse(successMessage, servers);
    }

    @GET
    @Path("joined")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getJoinedServers(@Context SecurityContext securityContext) {
        List<Server> servers = serverService.listServers();

        // current user
        Account user = us.resolve(securityContext);

        // make new list with server where user have joined
        servers.removeIf(server -> !server.getMembers().contains(user));

        String successMessage = "List of servers for user " + us.resolve(securityContext).getUsername();

        return API.createServerListResponse(successMessage, servers);
    }


    @POST
    @Path("join")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject joinServer(@Context SecurityContext securityContext,
                                 @FormParam("serverId") String serverId) {

        // verify if parameters are not null
        if(serverId == null || serverId.isEmpty()){
            return API.createErrorResponse("Missing parameters");
        }

        // verify if server id is a number
        try{
            Long.parseLong(serverId);
        }catch(NumberFormatException e){
            return API.createErrorResponse("Server id is not a number");
        }

        // verify if server exists
        Server server = serverService.get(Long.parseLong(serverId));

        if (server == null) {
            return API.createErrorResponse("Server does not exist");
        }

        Account user = us.resolve(securityContext);

        // verify if user is already in server
        if(server.getMembers().contains(user)){
            return API.createErrorResponse("User is already in server");
        }

        // add user to server
        var userlist = server.getMembers();
        userlist.add(user);
        server.setMembers(userlist);

        // add server to user
        var serverlist = user.getServers();
        serverlist.add(server);
        user.setServers(serverlist);

        String successMessage = "Joined successfully";

        return API.createResponse(successMessage, server);
    }



    @POST
    @Path("create")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject createServer(@Context SecurityContext securityContext,
                                 @FormParam("serverName") String serverName) {

        // verify if parameters are not null
        if(serverName == null || serverName.isEmpty()){
            return API.createErrorResponse("Missing parameters");
        }

        Account owner = us.resolve(securityContext);

        // create server
        Server server = new Server();
        server.setName(serverName);
        server.setOwner(owner);
        server.setMembers(List.of(owner));

        ServerChannel channel = new ServerChannel();
        channel.setName("General");
        channel.setMessages(Collections.emptyList());

        Category category = new Category();
        category.setName("General");
        category.setServer(server);
        category.setChannelsInCategory(List.of());

        category.setChannelsInCategory(List.of(channel));
        channel.setCategory(category);

        server.setCategories(List.of(category));

        entityManager.persist(channel);
        entityManager.persist(category);
        entityManager.persist(server);

        String successMessage = "Server created successfully";

        return API.createResponse(successMessage, server);
    }
}
