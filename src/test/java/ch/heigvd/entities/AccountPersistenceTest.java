package ch.heigvd.entities;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class AccountPersistenceTest {

    @Inject
    EntityManager entityManager;

    @Test
    @TestTransaction
    public void whenFindById_thenReturnAccount() {
        // given
        Account account = new Account();
        account.setUsername("testUser");
        account.setEmail("test@example.com");
        account.setPassword("myPassword");
        entityManager.persist(account);

        // when
        Account found = entityManager.find(Account.class, account.getId());

        // then
        assertNotNull(found);
        assertEquals(account.getUsername(), found.getUsername());
        assertEquals(account.getEmail(), found.getEmail());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        // when
        Account found = entityManager.find(Account.class, -1L);

        // then
        assertNull(found);
    }


    @Test
    @TestTransaction
    public void whenPersistingAccountWithNullUsername_thenThrowsException() {
        // given
        Account account = new Account();
        account.setUsername(null);
        account.setEmail("test@example.com");
        account.setPassword("myPassword");

        // when / then
        assertThrows(PersistenceException.class, () -> entityManager.persist(account));
    }

    @Test
    @TestTransaction
    public void whenPersistingAccountWithNullEmail_thenThrowsException() {
        // given
        Account account = new Account();
        account.setUsername("testUser");
        account.setEmail(null);
        account.setPassword("myPassword");

        // when / then
        assertThrows(PersistenceException.class, () -> entityManager.persist(account));
    }

    @Test
    @TestTransaction
    public void whenPersistingAccountWithNullPassword_thenThrowsException() {
        // given
        Account account = new Account();
        account.setUsername("testUser");
        account.setEmail("test@example.com");
        account.setPassword(null);

        // when / then
        assertThrows(PersistenceException.class, () -> entityManager.persist(account));
    }
}