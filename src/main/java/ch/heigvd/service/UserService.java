package ch.heigvd.service;

import ch.heigvd.entities.Account;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager em;

    public Account resolve(SecurityContext context) {
        TypedQuery<Account> query = em.createQuery("""
            SELECT a
            FROM Account a
            WHERE a.email = :email
        """, Account.class).setParameter("email", context.getUserPrincipal().getName());

        return query.getSingleResult();
    }
}
