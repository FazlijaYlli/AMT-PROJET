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
class ServerTest {
    private Server server;

    @BeforeEach
    void setUp() {
        server = new Server();
    }

    @Test
    void getIdAndSetId() {
        Long id = 1L;
        server.setId(id);
        assertEquals(id, server.getId());
    }

    @Test
    void getNameAndSetName() {
        String name = "testServer";
        server.setName(name);
        assertEquals(name, server.getName());
    }

    @Test
    void getOwnerAndSetOwner() {
        Account owner = new Account();
        owner.setUsername("testUser");
        server.setOwner(owner);
        assertEquals(owner, server.getOwner());
    }

    @Test
    void getCategoriesAndSetCategories() {
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setName("testCategory");
        categories.add(category);
        server.setCategories(categories);
        assertEquals(categories, server.getCategories());
    }

    @Test
    void getMembersAndSetMembers() {
        List<Account> members = new ArrayList<>();
        Account member = new Account();
        member.setUsername("testMember");
        members.add(member);
        server.setMembers(members);
        assertEquals(members, server.getMembers());
    }

    @Test
    void toJson() {
        server.setId(1L);
        server.setName("testServer");
        Account owner = new Account();
        owner.setId(2L);
        server.setOwner(owner);

        JsonObject actual = server.toJson().build();

        JsonObject expected = Json.createObjectBuilder()
                .add("id", 1L)
                .add("name", "testServer")
                .add("owner", 2L)
                .build();

        assertEquals(expected, actual);
    }
}