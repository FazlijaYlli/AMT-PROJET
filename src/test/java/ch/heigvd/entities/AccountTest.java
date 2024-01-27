package ch.heigvd.entities;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void getIdAndSetId() {
        Long id = 1L;
        account.setId(id);
        assertEquals(id, account.getId());
    }

    @Test
    void getUsernameAndSetUsername() {
        String username = "testUser";
        account.setUsername(username);
        assertEquals(username, account.getUsername());
    }

    @Test
    void getEmailAndSetEmail() {
        String email = "test@example.com";
        account.setEmail(email);
        assertEquals(email, account.getEmail());
    }

    @Test
    void getPasswordAndSetPassword() {
        String password = "password";
        account.setPassword(password);
        assertEquals(password, account.getPassword());
    }

    @Test
    void getMessagesAndSetMessages() {
        List<Message> messages = new ArrayList<>();
        account.setMessages(messages);
        assertEquals(messages, account.getMessages());
    }

    @Test
    void getServersAndSetServers() {
        List<Server> servers = new ArrayList<>();
        account.setServers(servers);
        assertEquals(servers, account.getServers());
    }

    @Test
    void getDirectMessagesAndSetDirectMessages() {
        List<PrivateChannel> directMessages = new ArrayList<>();
        account.setDirectMessages(directMessages);
        assertEquals(directMessages, account.getDirectMessages());
    }

    @Test
    void getServersOwnerAndSetServersOwner() {
        List<Server> serversOwner = new ArrayList<>();
        account.setServersOwner(serversOwner);
        assertEquals(serversOwner, account.getServersOwner());
    }

    @Test
    void toJson() {

        account.setId(1L);
        account.setUsername("testUser");

        JsonObject actual = account.toJson().build();

        JsonObject expected = Json.createObjectBuilder()
                .add("id", 1L)
                .add("username", "testUser")
                .build();

        assertEquals(expected, actual);
    }
}