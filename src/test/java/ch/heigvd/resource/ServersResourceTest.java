package ch.heigvd.resource;

import ch.heigvd.entities.Account;
import ch.heigvd.entities.Server;
import ch.heigvd.service.ServerService;
import ch.heigvd.service.UserService;
import io.quarkus.test.TestTransaction;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ServersResourceTest {

    private ServersResource serversResource;
    private ServerService serverService;
    private UserService userService;
    private SecurityContext securityContext;

    private Account user;
    private Server server;

    @BeforeEach
    @TestTransaction
    void initialize() {
        serverService = Mockito.mock(ServerService.class);
        userService = Mockito.mock(UserService.class);
        securityContext = Mockito.mock(SecurityContext.class);
        serversResource = new ServersResource();
        serversResource.serverService = serverService;
        serversResource.us = userService;

        user = new Account();
        user.setId(1L);
        user.setUsername("newUser");
        user.setEmail("email@test.com");

        server = new Server();
        server.setId(1L);
        server.setName("Server 1");
        server.setOwner(user);
    }

    @Test
    void shouldReturnAllServers() {
        when(serverService.listServers()).thenReturn(Arrays.asList(server, server));

        JsonObject response = serversResource.getServers();

        assertEquals("200", response.getString("status"));
        assertEquals("List of all servers", response.getString("message"));
        assertEquals(2, response.getJsonArray("data").size());
    }

    @Test
    void shouldReturnServersUserHasJoined() {
        server.setMembers(Collections.singletonList(user));
        when(serverService.listServers()).thenReturn(Arrays.asList(server, server));
        when(userService.resolve(securityContext)).thenReturn(user);

        JsonObject response = serversResource.getJoinedServers(securityContext);

        assertEquals("200", response.getString("status"));
        assertEquals("List of servers for user newUser", response.getString("message"));
        assertEquals(2, response.getJsonArray("data").size());
    }

    @Test
    void shouldReturnErrorWhenServerDoesNotExist() {
        when(serverService.get(1L)).thenReturn(null);

        JsonObject response = serversResource.joinServer(securityContext, "1");

        assertEquals("400", response.getString("status"));
        assertEquals("Server does not exist", response.getString("message"));
    }

    @Test
    void shouldReturnCreatedServer() {
        when(userService.resolve(securityContext)).thenReturn(user);
        when(serverService.createServer("Server 1", user)).thenReturn(server);

        JsonObject response = serversResource.createServer(securityContext, "Server 1");

        assertEquals("200", response.getString("status"));
        assertEquals("Server created successfully", response.getString("message"));
        assertEquals(server.toJson().build(), response.getJsonObject("data"));
    }
}