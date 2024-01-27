package ch.heigvd.service;

import ch.heigvd.entities.Account;
import ch.heigvd.entities.Category;
import ch.heigvd.entities.Server;
import ch.heigvd.entities.ServerChannel;
import io.quarkus.test.TestTransaction;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ServerServiceTest {

    @Inject
    ServerService serverService;

    @Inject
    EntityManager em;

    @BeforeEach
    void setup() {
        em = Mockito.mock(EntityManager.class);
        serverService = new ServerService();
        serverService.em = em;
    }

    @Test
    @TestTransaction
    void createServerShouldReturnServerWithCorrectProperties() {
        Account owner = new Account();
        owner.setId(1L);
        owner.setUsername("newUser");
        owner.setEmail("new@example.com");
        owner.setPassword("password");

        Server server = serverService.createServer("Server 1", owner);

        assertEquals("Server 1", server.getName());
        assertEquals(owner, server.getOwner());
        assertEquals(1, server.getMembers().size());
        assertEquals(owner, server.getMembers().get(0));

        Category category = server.getCategories().get(0);
        assertEquals("General", category.getName());
        assertEquals(server, category.getServer());

        ServerChannel channel = category.getChannelsInCategory().get(0);
        assertEquals("General", channel.getName());
        assertEquals(category, channel.getCategory());
    }
}