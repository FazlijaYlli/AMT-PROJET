package ch.heigvd.entities;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ServerPersistenceTest {

    @Inject
    EntityManager entityManager;

    @Test
    @TestTransaction
    public void whenPersistingServer_thenServerIsPersisted() {
        // given
        Account owner = new Account();
        owner.setUsername("testUser");
        owner.setEmail("testUser@test.com");
        owner.setPassword("testPassword");
        entityManager.persist(owner);

        Server server = new Server();
        server.setName("testServer");
        server.setOwner(owner);
        entityManager.persist(server);

        // when
        Server found = entityManager.find(Server.class, server.getId());

        // then
        assertNotNull(found);
        assertEquals(server.getName(), found.getName());
        assertEquals(owner.getId(), found.getOwner().getId());
    }

    @Test
    @TestTransaction
    public void whenPersistingServerWithNullName_thenThrowsException() {
        // given
        Server server = new Server();
        server.setName(null);

        // when / then
        assertThrows(PersistenceException.class, () -> entityManager.persist(server));
    }

    @Test
    @TestTransaction
    public void whenPersistingServerWithEmptyName_thenThrowsException() {
        // given
        Server server = new Server();
        server.setName("");

        // when / then
        assertThrows(ConstraintViolationException.class, () -> entityManager.persist(server));
    }
}