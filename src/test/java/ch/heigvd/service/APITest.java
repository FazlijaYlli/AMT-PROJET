package ch.heigvd.service;

import ch.heigvd.entities.*;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class APITest {

    private Server server;
    private Category category;
    private ServerChannel channel;
    private Message message;
    private Account account;

    @BeforeEach
    void setup() {

        account = new Account();
        account.setId(1L);
        account.setUsername("testUser");
        account.setEmail("test@user.com");
        account.setPassword("password");

        message = new Message();
        message.setId(1L);
        message.setText("Hello, world!");
        message.setAuthor(account);
        message.setTimestamp(Timestamp.from(java.time.Instant.now()));

        channel = new ServerChannel();
        channel.setId(1L);
        channel.setName("General");
        channel.setMessages(Collections.singletonList(message));

        category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        category.setChannelsInCategory(Collections.singletonList(channel));


        server = new Server();
        server.setId(1L);
        server.setName("Server 1");
        server.setOwner(account);
        server.setCategories(Collections.singletonList(category));
    }

    @Test
    void createResponseShouldReturnCorrectResponse() {
        JsonObject response = API.createResponse("Success", server);
        assertEquals("200", response.getString("status"));
        assertEquals("Success", response.getString("message"));
        assertEquals(server.toJson().build(), response.getJsonObject("data"));
    }

    @Test
    void createErrorResponseShouldReturnCorrectResponse() {
        JsonObject response = API.createErrorResponse("Error");
        assertEquals("400", response.getString("status"));
        assertEquals("Error", response.getString("message"));
    }

    @Test
    void createServerListResponseShouldReturnCorrectResponse() {
        JsonObject response = API.createServerListResponse("Success", Arrays.asList(server, server));
        assertEquals("200", response.getString("status"));
        assertEquals("Success", response.getString("message"));
        assertEquals(2, response.getJsonArray("data").size());
    }

    @Test
    void createCategoryAndChannelListResponseShouldReturnCorrectResponse() {
        JsonObject response = API.createCategoryAndChannelListResponse("Success", server);
        assertEquals("200", response.getString("status"));
        assertEquals("Success", response.getString("message"));
        assertEquals(1, response.getJsonObject("data").getJsonArray("categories").size());
    }

    @Test
    void createMessagesResponseShouldReturnCorrectResponse() {
        JsonObject response = API.createMessagesResponse("Success", channel);
        assertEquals("200", response.getString("status"));
        assertEquals("Success", response.getString("message"));
        assertEquals(1, response.getJsonObject("data").getJsonArray("messages").size());
    }
}