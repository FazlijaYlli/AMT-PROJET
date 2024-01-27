package ch.heigvd.service;

import ch.heigvd.entities.Account;
import ch.heigvd.entities.Category;
import ch.heigvd.entities.Server;
import ch.heigvd.entities.ServerChannel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ServerService {

    @Inject
    EntityManager em;

    @Transactional
    public Server createServer(String serverName, Account owner) {
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

        em.persist(server);
        em.persist(category);
        em.persist(channel);

        return server;
    }

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

        return query.getResultList().get(0);
    }
}
