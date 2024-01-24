package ch.heigvd.service;

import ch.heigvd.entities.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
public class ServerChannelService {

    @Inject
    EntityManager em;

    public ServerChannel create(Category category, String channelName) {
        ServerChannel channel = new ServerChannel();
        channel.setName(channelName);
        channel.setCategory(category);

        em.persist(channel);

        return channel;
    }

    public ServerChannel get(Long channelId) {
        // TODO: Fix this query
        TypedQuery<ServerChannel> query = em.createQuery("""
                SELECT channel
                FROM ServerChannel channel
                JOIN channel.messages messages
                WHERE channel.id = :id
        """, ServerChannel.class).setParameter("id", channelId);

        return query.getSingleResult();
    }
}
