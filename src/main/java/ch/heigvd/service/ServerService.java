package ch.heigvd.service;

import ch.heigvd.entities.Server;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
public class ServerService {

    @Inject
    EntityManager em;

    public List<Server> listServers() {
        TypedQuery<Server> query = em.createQuery("""
                SELECT server
                FROM Server server
        """, Server.class);

        return query.getResultList();
    }

    public Server get(Long id) {
        TypedQuery<Server> query = em.createQuery("""
                SELECT server
                FROM Server server
                JOIN server.categories cats
                JOIN cats.channelsInCategory chans
                WHERE server.id = :id
        """, Server.class).setParameter("id", id);

        return query.getSingleResult();
    }
}
